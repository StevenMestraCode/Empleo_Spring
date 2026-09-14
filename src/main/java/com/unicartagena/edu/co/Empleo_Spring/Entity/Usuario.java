package com.unicartagena.edu.co.Empleo_Spring.Entity;

import jakarta.persistence.*; // Importación correcta para Spring Boot 3+
import lombok.Data;

@Data // Lombok genera getters, setters, toString, equals y hashCode
@Entity
@Table(name = "usuarios") // Nombre de la tabla en la base de datos
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El documento pide "nombre"
    @Column(nullable = false)
    private String nombre;

    // El documento pide "clave"
    @Column(nullable = false)
    private String clave;

    // El documento pide "rol"
    @Column(nullable = false)
    private String rol;

    // EXTRA: Necesario para el punto 4.9 (Recuperación de clave por correo)
    @Column(nullable = false, unique = true)
    private String correo;

    // Nota: No se necesita escribir los getters y setters manualmente gracias a @Data
}