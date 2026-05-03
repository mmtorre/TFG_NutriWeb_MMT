package com.nutriweb100.dto;

public record MacrosUsuarioRequest(
        int edad,
        double altura,
        double peso,
        String sexo,
        String nivelActividad,
        String objetivo
) {
}

