package com.nutriweb100.controller;

import com.nutriweb100.dto.LoginRequest;
import com.nutriweb100.dto.LoginResponse;
import com.nutriweb100.model.Usuario;
import com.nutriweb100.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.email() == null || loginRequest.email().isBlank()
                || loginRequest.password() == null || loginRequest.password().isBlank()) {
            return ResponseEntity.badRequest().body("El email y la contrasena son obligatorios");
        }

        return usuarioRepository.findByEmail(loginRequest.email())
                .filter(usuario -> passwordEncoder.matches(loginRequest.password(), usuario.getPassword()))
                .<ResponseEntity<?>>map(this::buildSuccessResponse)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Credenciales incorrectas"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String nombre = request.get("nombre");
        String email = request.get("email");
        String password = request.get("password");

        if (nombre == null || email == null || password == null) {
            return ResponseEntity.badRequest().body("Todos los campos son obligatorios");
        }

        if (usuarioRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol("USER");
        usuario.setFechaRegistro(LocalDateTime.now());

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("message", "Usuario registrado correctamente"));
    }

    private ResponseEntity<LoginResponse> buildSuccessResponse(Usuario usuario) {
        LoginResponse response = new LoginResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                "Login correcto"
        );

        return ResponseEntity.ok(response);
    }
}
