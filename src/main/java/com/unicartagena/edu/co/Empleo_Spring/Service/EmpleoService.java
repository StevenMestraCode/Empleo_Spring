package com.unicartagena.edu.co.Empleo_Spring.Service;

import com.unicartagena.edu.co.Empleo_Spring.Entity.Empleo;
import java.util.List;
import java.util.Optional;

public interface EmpleoService {
    List<Empleo> listarTodos();
    Empleo guardar(Empleo empleo);
    Optional<Empleo> buscarPorId(Long id);
    void eliminar(Long id);
    List<Empleo> buscarPorCategoria(String categoria);
    List<Empleo> buscarPorEmpresa(String empresa);
}