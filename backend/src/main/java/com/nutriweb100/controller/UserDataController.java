package com.nutriweb100.controller;

import com.nutriweb100.dto.UserDataRequest;
import com.nutriweb100.dto.UserDataResponse;
import com.nutriweb100.model.RegistroNutricional;
import com.nutriweb100.model.Usuario;
import com.nutriweb100.repository.RegistroRepository;
import com.nutriweb100.repository.UsuarioRepository;
import com.nutriweb100.service.CalculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/userdata")
@CrossOrigin
public class UserDataController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private CalculoService calculoService;

    @PostMapping
    public ResponseEntity<?> guardarDatosUsuario(@RequestBody UserDataRequest request) {
        return actualizarDatosUsuario(request);
    }

    @PutMapping
    public ResponseEntity<?> actualizarDatosUsuario(@RequestBody UserDataRequest request) {
        if (request.email() == null || request.email().isBlank()
                || request.edad() <= 0 || request.peso() <= 0 || request.altura() <= 0
                || request.sexo() == null || request.sexo().isBlank()
                || request.nivelActividad() == null || request.nivelActividad().isBlank()) {
            return ResponseEntity.badRequest().body("Todos los datos del usuario son obligatorios y deben ser validos");
        }

        return usuarioRepository.findByEmail(request.email())
                .<ResponseEntity<?>>map(usuario -> actualizarUsuario(usuario, request))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<UserDataResponse> actualizarUsuario(Usuario usuario, UserDataRequest request) {
        usuario.setEdad(request.edad());
        usuario.setSexo(request.sexo());
        usuario.setPeso(request.peso());
        usuario.setAltura(request.altura());
        usuario.setNivelActividad(request.nivelActividad());
        usuario.setObjetivo(request.objetivo());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        double imc = calculoService.calcularIMC(usuarioActualizado.getPeso(), usuarioActualizado.getAltura());
        int calorias = calculoService.calcularCalorias(
                usuarioActualizado.getSexo(),
                usuarioActualizado.getPeso(),
                usuarioActualizado.getAltura(),
                usuarioActualizado.getEdad()
        );
        double masaMuscular = calculoService.calcularMasaMuscular(
                usuarioActualizado.getPeso(),
                usuarioActualizado.getSexo()
        );

        RegistroNutricional registro = new RegistroNutricional();
        registro.setUsuario(usuarioActualizado);
        registro.setImc(imc);
        registro.setCaloriasRecomendadas(calorias);
        registro.setMasaMuscular(masaMuscular);
        registro.setFecha(LocalDateTime.now());
        registroRepository.save(registro);

        UserDataResponse response = new UserDataResponse(
                usuarioActualizado.getId(),
                usuarioActualizado.getNombre(),
                usuarioActualizado.getEmail(),
                usuarioActualizado.getEdad(),
                usuarioActualizado.getSexo(),
                usuarioActualizado.getPeso(),
                usuarioActualizado.getAltura(),
                usuarioActualizado.getNivelActividad(),
                usuarioActualizado.getObjetivo(),
                imc,
                calorias,
                masaMuscular,
                "Datos del usuario actualizados correctamente"
        );

        return ResponseEntity.ok(response);
    }
}
