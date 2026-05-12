package com.nutriweb100;

import com.nutriweb100.dto.MacrosComida;
import com.nutriweb100.service.CalculoService;
import com.nutriweb100.service.MacrosService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NutriWebApplicationTests {

    private static MacrosService newMacrosServiceWithRealCalculoService() {
        MacrosService macrosService = new MacrosService();
        try {
            Field f = MacrosService.class.getDeclaredField("calculoService");
            f.setAccessible(true);
            f.set(macrosService, new CalculoService());
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("No se pudo inyectar CalculoService en MacrosService para el test", e);
        }
        return macrosService;
    }

	@Test
	void calculoService_calcularIMC_conAlturaEnCm() {
        CalculoService s = new CalculoService();
        double imc = s.calcularIMC(70.0, 175.0);
        assertEquals(22.857142857, imc, 1e-6);
    }

    @Test
    void calculoService_calcularCalorias_normalizaSexo() {
        CalculoService s = new CalculoService();

        // Hombre
        assertEquals(1673, s.calcularCalorias("hombre", 70.0, 175.0, 25));
        assertEquals(1673, s.calcularCalorias("MASCULINO", 70.0, 175.0, 25));
        assertEquals(1673, s.calcularCalorias(" masc ", 70.0, 175.0, 25));

        // Mujer
        assertEquals(1507, s.calcularCalorias("mujer", 70.0, 175.0, 25));
        assertEquals(1507, s.calcularCalorias("FEMENINO", 70.0, 175.0, 25));
    }

    @Test
    void calculoService_calcularCalorias_sexoInvalido_lanzaExcepcion() {
        CalculoService s = new CalculoService();
        assertThrows(IllegalArgumentException.class, () -> s.calcularCalorias("otro", 70.0, 175.0, 25));
        assertThrows(IllegalArgumentException.class, () -> s.calcularCalorias(null, 70.0, 175.0, 25));
    }

    @Test
    void calculoService_calcularTDEE_aplicaFactorActividad() {
        CalculoService s = new CalculoService();
        // BMR (hombre) = 1673, moderado = 1.55 => 2593.15 -> 2593
        assertEquals(2593, s.calcularTDEE("hombre", 70.0, 175.0, 25, "moderado"));
        // default si actividad desconocida => sedentario (1.2)
        assertEquals(2007, s.calcularTDEE("hombre", 70.0, 175.0, 25, "???"));
    }

    @Test
    void calculoService_calcularMasaMuscular_porSexo() {
        CalculoService s = new CalculoService();
        assertEquals(31.5, s.calcularMasaMuscular(70.0, "hombre"), 1e-9);
        assertEquals(28.0, s.calcularMasaMuscular(70.0, "mujer"), 1e-9);
    }

    @Test
    void macrosService_calcularMacros_devuelvePlanConClavesYOrdenEstable() {
        MacrosService macrosService = newMacrosServiceWithRealCalculoService();

        Map<String, MacrosComida> plan = macrosService.calcularMacros(
                "hombre",
                70.0,
                175.0,
                25,
                "moderado",
                "mantenimiento"
        );

        List<String> keys = new ArrayList<>(plan.keySet());
        assertEquals(List.of("desayuno", "almuerzo", "preentreno", "cena"), keys);
        assertEquals(4, plan.size());
    }

    @Test
    void macrosService_calcularMacros_mantenimiento_sumaCaloriasCasiExacta() {
        MacrosService macrosService = newMacrosServiceWithRealCalculoService();

        // tdee=2593 para estos inputs (ver test de CalculoService)
        Map<String, MacrosComida> plan = macrosService.calcularMacros(
                "hombre",
                70.0,
                175.0,
                25,
                "moderado",
                "mantenimiento"
        );

        int sum = plan.values().stream().mapToInt(MacrosComida::calorias).sum();
        // Por casting a int por comida puede haber una pérdida de 0-3 kcal.
        assertTrue(Math.abs(2593 - sum) <= 3, "Suma calorias=" + sum);
    }

    @Test
    void macrosService_calcularMacros_objetivoVolumen_normalizaYAumentaCalorias() {
        MacrosService macrosService = newMacrosServiceWithRealCalculoService();

        Map<String, MacrosComida> mantenimiento = macrosService.calcularMacros(
                "hombre", 70.0, 175.0, 25, "moderado", "mantenimiento"
        );
        Map<String, MacrosComida> volumen = macrosService.calcularMacros(
                "hombre", 70.0, 175.0, 25, "moderado", "volumen"
        );

        int kcalMantenimiento = mantenimiento.values().stream().mapToInt(MacrosComida::calorias).sum();
        int kcalVolumen = volumen.values().stream().mapToInt(MacrosComida::calorias).sum();

        // "volumen" => masa_muscular => +300 (puede variar 0-3 por comida)
        assertTrue(kcalVolumen >= kcalMantenimiento + 297, "kcalMantenimiento=" + kcalMantenimiento + ", kcalVolumen=" + kcalVolumen);
    }

    @Test
    void macrosService_calcularMacros_perdidaGrasa_respetaMinimo1200() {
        MacrosService macrosService = newMacrosServiceWithRealCalculoService();

        // Valores pequeñoss para forzar tdee bajo; con -400 debe quedar por debajo de 1200
        Map<String, MacrosComida> plan = macrosService.calcularMacros(
                "mujer",
                40.0,
                140.0,
                18,
                "sedentario",
                "perdida_grasa"
        );

        int sum = plan.values().stream().mapToInt(MacrosComida::calorias).sum();
        assertTrue(Math.abs(1200 - sum) <= 3, "Suma calorias=" + sum);
    }

}
