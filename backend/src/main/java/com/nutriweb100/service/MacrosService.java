package com.nutriweb100.service;

import com.nutriweb100.dto.MacrosComida;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MacrosService {

    @Autowired
    private CalculoService calculoService;

    private String normalizarObjetivo(String objetivo) {
        if (objetivo == null) {
            return "";
        }

        String valor = objetivo.trim().toLowerCase().replace(' ', '_');

        if (valor.equals("ganar_masa_muscular") || valor.equals("ganar_masa") || valor.equals("volumen")) {
            return "masa_muscular";
        }

        return valor;
    }

    public Map<String, MacrosComida> calcularMacros(String sexo,
                                                    double peso,
                                                    double altura,
                                                    int edad,
                                                    String actividad,
                                                    String objetivo) {

        int tdee = calculoService.calcularTDEE(sexo, peso, altura, edad, actividad);

        String objetivoLimpio = normalizarObjetivo(objetivo);

        // Ajustar calorias segun objetivo
        int caloriasObjetivo = switch (objetivoLimpio) {
            case "perdida_grasa" -> tdee - 400;
            case "masa_muscular" -> tdee + 300;
            default -> tdee;
        };

        // Minimo saludable
        if (caloriasObjetivo < 1200) {
            caloriasObjetivo = 1200;
        }

        double proteinasTotal = switch (objetivoLimpio) {
            case "perdida_grasa" -> peso * 2.0;
            case "mantenimiento" -> peso * 1.6;
            case "masa_muscular" -> peso * 2.2;
            default -> peso * 1.6;
        };

        double grasasTotal = switch (objetivoLimpio) {
            case "perdida_grasa" -> peso * 0.8;
            case "mantenimiento" -> peso * 1.0;
            case "masa_muscular" -> peso * 1.0;
            default -> peso * 1.0;
        };

        double caloriasCarbs = caloriasObjetivo - (proteinasTotal * 4) - (grasasTotal * 9);
        double carbohidratosTotal = Math.max(0, caloriasCarbs / 4);

        Map<String, MacrosComida> plan = new LinkedHashMap<>();
        plan.put("desayuno", calcularComida(caloriasObjetivo, proteinasTotal, carbohidratosTotal, grasasTotal, 0.25));
        plan.put("almuerzo", calcularComida(caloriasObjetivo, proteinasTotal, carbohidratosTotal, grasasTotal, 0.35));
        plan.put("preentreno", calcularComida(caloriasObjetivo, proteinasTotal, carbohidratosTotal, grasasTotal, 0.15));
        plan.put("cena", calcularComida(caloriasObjetivo, proteinasTotal, carbohidratosTotal, grasasTotal, 0.25));

        return plan;
    }

    private MacrosComida calcularComida(int caloriasTotal,
                                        double proteinas,
                                        double carbohidratos,
                                        double grasas,
                                        double porcentaje) {
        return new MacrosComida(
                (int) (caloriasTotal * porcentaje),
                Math.round(proteinas * porcentaje * 10.0) / 10.0,
                Math.round(carbohidratos * porcentaje * 10.0) / 10.0,
                Math.round(grasas * porcentaje * 10.0) / 10.0
        );
    }
}
