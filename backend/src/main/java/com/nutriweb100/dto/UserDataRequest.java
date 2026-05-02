package com.nutriweb100.dto;

public record UserDataRequest(
        String email,
        int edad,
        String sexo,
        double peso,
        double altura,
        String nivelActividad,
        String objetivo
) {
}
