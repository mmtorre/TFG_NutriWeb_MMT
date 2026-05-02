package com.nutriweb100.dto;

public record LoginResponse(
        Long id,
        String nombre,
        String email,
        String rol,
        String mensaje
) {
}
