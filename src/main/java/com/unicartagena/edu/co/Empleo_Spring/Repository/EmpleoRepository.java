package com.unicartagena.edu.co.Empleo_Spring.Repository;

import com.unicartagena.edu.co.Empleo_Spring.Entity.Empleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleoRepository extends JpaRepository<Empleo, Long> {

    // Reporte 1: Empleos por categoría
    List<Empleo> findByCategoria(String categoria);

    // Reporte 2: Empleos por empresa
    List<Empleo> findByEmpresaContainingIgnoreCase(String empresa);
}