package com.nutriweb100.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data //se implementan getters&setters automáticamente
@Entity
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String password;
    private int edad;
    private double peso;
    private double altura;
    private String sexo;

    @Column(name = "nivel_actividad")
    private String nivelActividad;

    private String objetivo;

    private String rol;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;


}
