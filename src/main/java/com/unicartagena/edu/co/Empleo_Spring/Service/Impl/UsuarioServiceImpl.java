package com.unicartagena.edu.co.Empleo_Spring.Service.Impl;

import com.unicartagena.edu.co.Empleo_Spring.Entity.Usuario;
import com.unicartagena.edu.co.Empleo_Spring.Repository.UsuarioRepository;
import com.unicartagena.edu.co.Empleo_Spring.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        // Si el usuario es nuevo O la clave NO está encriptada aún, la encriptamos
        if (usuario.getId() == null ||
                !usuario.getClave().startsWith("$2a$")) {
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<Usuario> buscarPorRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }

    @Override
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Override
    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override
    public String generarClaveTemporal(String correo) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Genera una clave aleatoria de 8 caracteres
            String claveTemporal = UUID.randomUUID().toString().substring(0, 8);
            usuario.setClave(passwordEncoder.encode(claveTemporal));
            usuarioRepository.save(usuario);
            return claveTemporal; // Se devuelve para enviarla por correo
        }
        return null;
    }
}