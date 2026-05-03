package com.nutriweb100.service;

import org.springframework.stereotype.Service;

@Service
public class CalculoService {
    public double calcularIMC(double peso, double altura) {
        return peso / Math.pow(altura / 100, 2);
    }

    //BMR
    public int calcularCalorias(String sexo, double peso, double altura, int edad) {
        String sexoNormalizado = normalizarSexo(sexo);

        if (sexoNormalizado.equals("fem")) {
            return (int) ((10 * peso) + (6.25 * altura) - (5 * edad) - 161);
        }

        if (sexoNormalizado.equals("masc")) {
            return (int) ((10 * peso) + (6.25 * altura) - (5 * edad) + 5);
        }

        throw new IllegalArgumentException("Sexo no valido");
    }

    public int calcularTDEE(String sexo, double peso, double altura, int edad, String actividad) {
        int bmr = calcularCalorias(sexo, peso, altura, edad);

        String actividadLimpia = actividad != null ? actividad.trim().toLowerCase() : "";
        double factorActividad = switch (actividadLimpia) {
            case "sedentario" -> 1.2;
            case "ligero" -> 1.375;
            case "moderado" -> 1.55;
            case "activo" -> 1.725;
            case "muy_activo" -> 1.9;
            default -> 1.2;
        };

        return (int) (bmr * factorActividad);
    }

    public double calcularMasaMuscular(double peso, String sexo) {
        String sexoNormalizado = normalizarSexo(sexo);

        if (sexoNormalizado.equals("masc")) {
            return peso * 0.45;
        }

        if (sexoNormalizado.equals("fem")) {
            return peso * 0.40;
        }

        throw new IllegalArgumentException("Sexo no valido");
    }

    private String normalizarSexo(String sexo) {
        if (sexo == null) {
            throw new IllegalArgumentException("Sexo no valido");
        }

        String valor = sexo.trim().toLowerCase();

        if (valor.equals("masc") || valor.equals("masculino") || valor.equals("hombre")) {
            return "masc";
        }

        if (valor.equals("fem") || valor.equals("femenino") || valor.equals("mujer")) {
            return "fem";
        }

        throw new IllegalArgumentException("Sexo no valido");
    }
}
