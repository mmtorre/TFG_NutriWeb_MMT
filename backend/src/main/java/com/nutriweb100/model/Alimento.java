package com.nutriweb100.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="alimentos")
public class Alimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int calorias;
    private double proteinas;
    private double carbohidratos;
    private double grasas;
}
