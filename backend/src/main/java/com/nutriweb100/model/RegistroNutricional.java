package com.nutriweb100.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "registros_nutricionales")
public class RegistroNutricional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double imc;

    @Column(name = "calorias_recomendadas")
    private int caloriasRecomendadas;

    @Column(name = "masa_muscular")
    private double masaMuscular;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}

