package com.unicartagena.edu.co.Empleo_Spring.Service;

import com.unicartagena.edu.co.Empleo_Spring.Entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    // CRUD
    List<Usuario> listarTodos();
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    void eliminar(Long id);

    // Reportes parametrizados
    List<Usuario> buscarPorRol(String rol);
    List<Usuario> buscarPorNombre(String nombre);

    // Login y utilidades
    Optional<Usuario> buscarPorCorreo(String correo);
    boolean existeCorreo(String correo);

    // Recuperación de clave
    String generarClaveTemporal(String correo);
}