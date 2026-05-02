package com.nutriweb100.controller;

import com.nutriweb100.dto.LoginRequest;
import com.nutriweb100.dto.LoginResponse;
import com.nutriweb100.model.Usuario;
import com.nutriweb100.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.email() == null || loginRequest.email().isBlank()
                || loginRequest.password() == null || loginRequest.password().isBlank()) {
            return ResponseEntity.badRequest().body("El email y la contrasena son obligatorios");
        }

        return usuarioRepository.findByEmail(loginRequest.email())
                .filter(usuario -> usuario.getPassword().equals(loginRequest.password()))
                .<ResponseEntity<?>>map(this::buildSuccessResponse)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Credenciales incorrectas"));
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
