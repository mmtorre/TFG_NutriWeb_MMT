package com.nutriweb100.dto;

public record UserDataResponse(
        Long id,
        String nombre,
        String email,
        int edad,
        String sexo,
        double peso,
        double altura,
        String nivelActividad,
        String objetivo,
        double imc,
        int calorias,
        double masaMuscular,
        String mensaje
) {
}
