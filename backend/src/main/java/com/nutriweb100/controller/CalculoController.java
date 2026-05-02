package com.nutriweb100.controller;

import com.nutriweb100.model.RegistroNutricional;
import com.nutriweb100.model.Usuario;
import com.nutriweb100.repository.RegistroRepository;
import com.nutriweb100.repository.UsuarioRepository;
import com.nutriweb100.service.CalculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CalculoController {
    @Autowired
    private CalculoService service;

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/calculo")
    public ResponseEntity<?> calcular(@RequestBody Map<String, Object> datos) {
        try {
            double peso = Double.parseDouble(datos.get("peso").toString());
            double altura = Double.parseDouble(datos.get("altura").toString());
            int edad = Integer.parseInt(datos.get("edad").toString());
            String sexo = datos.get("sexo").toString();

            double imc = service.calcularIMC(peso, altura);
            int calorias = service.calcularCalorias(sexo, peso, altura, edad);
            double masa = service.calcularMasaMuscular(peso, sexo);

            RegistroNutricional registro = new RegistroNutricional();
            registro.setImc(imc);
            registro.setCaloriasRecomendadas(calorias);
            registro.setMasaMuscular(masa);
            registro.setFecha(LocalDateTime.now());

            Object email = datos.get("email");
            if (email != null && !email.toString().isBlank()) {
                Usuario usuario = usuarioRepository.findByEmail(email.toString()).orElse(null);
                registro.setUsuario(usuario);
            }

            registroRepository.save(registro);

            Map<String, Object> response = new HashMap<>();
            response.put("imc", imc);
            response.put("calorias", calorias);
            response.put("masaMuscular", masa);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo realizar el calculo");
        }
    }
}
