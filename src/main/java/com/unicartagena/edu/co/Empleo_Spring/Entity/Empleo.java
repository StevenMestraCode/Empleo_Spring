package com.unicartagena.edu.co.Empleo_Spring.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "empleo")
public class Empleo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String categoria;

    @Column(name = "area_trabajo")
    private String areaTrabajo;

    private String empresa;

    private String nivel;

    private String sueldo;

    private String funciones;

    @Column(name = "cargo_jefe")
    private String cargoJefe;
}
