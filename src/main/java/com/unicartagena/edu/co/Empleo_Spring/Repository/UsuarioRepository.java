package com.unicartagena.edu.co.Empleo_Spring.Repository;

import com.unicartagena.edu.co.Empleo_Spring.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Necesario para el Login (Spring Security lo usará)
    Optional<Usuario> findByCorreo(String correo);

    // Reporte 1: Buscar usuarios por rol (parametrizado)
    java.util.List<Usuario> findByRol(String rol);

    // Reporte 2: Buscar usuarios por coincidencia en el nombre (parametrizado)
    java.util.List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    // Para validar que no se registre un correo duplicado
    boolean existsByCorreo(String correo);
}