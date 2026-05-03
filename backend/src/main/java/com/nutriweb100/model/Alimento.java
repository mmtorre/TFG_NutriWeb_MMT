package com.nutriweb100.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    // En BD se usa como categoría de comida (desayuno/almuerzo/preentreno/cena).
    // El repository actual asume que la columna es un array 
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "categoria", columnDefinition = "text[]")
    private String[] categoria;
}
