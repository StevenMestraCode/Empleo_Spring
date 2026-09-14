package com.unicartagena.edu.co.Empleo_Spring.Service.Impl;

import com.unicartagena.edu.co.Empleo_Spring.Entity.Empleo;
import com.unicartagena.edu.co.Empleo_Spring.Repository.EmpleoRepository;
import com.unicartagena.edu.co.Empleo_Spring.Service.EmpleoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleoServiceImpl implements EmpleoService {

    @Autowired
    private EmpleoRepository empleoRepository;

    @Override
    public List<Empleo> listarTodos() {
        return empleoRepository.findAll();
    }

    @Override
    public Empleo guardar(Empleo empleo) {
        return empleoRepository.save(empleo);
    }

    @Override
    public Optional<Empleo> buscarPorId(Long id) {
        return empleoRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        empleoRepository.deleteById(id);
    }

    @Override
    public List<Empleo> buscarPorCategoria(String categoria) {
        return empleoRepository.findByCategoria(categoria);
    }

    @Override
    public List<Empleo> buscarPorEmpresa(String empresa) {
        return empleoRepository.findByEmpresaContainingIgnoreCase(empresa);
    }
}
