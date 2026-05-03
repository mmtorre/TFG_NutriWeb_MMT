package com.nutriweb100.controller;

import com.nutriweb100.dto.MacrosComida;
import com.nutriweb100.service.MacrosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/macros")
@CrossOrigin
@RequiredArgsConstructor
public class MacrosController {

    private final MacrosService macrosService;

    @GetMapping
    public ResponseEntity<Map<String, MacrosComida>> calcularMacros(
            @RequestParam String sexo,
            @RequestParam double peso,
            @RequestParam double altura,
            @RequestParam int edad,
            @RequestParam String actividad,
            @RequestParam String objetivo) {

        Map<String, MacrosComida> macros = macrosService.calcularMacros(sexo, peso, altura, edad, actividad, objetivo);
        return ResponseEntity.ok(macros);
    }
}