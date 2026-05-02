package com.nutriweb100.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="registro_alimentos")
public class RegistroAlimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "registro_id")
    private RegistroNutricional registro;

    @ManyToOne
    @JoinColumn(name = "alimento_id")
    private Alimento alimento;

}
