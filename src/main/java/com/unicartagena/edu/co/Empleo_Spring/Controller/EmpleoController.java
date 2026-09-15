package com.unicartagena.edu.co.Empleo_Spring.Controller;

import com.unicartagena.edu.co.Empleo_Spring.Entity.Empleo;
import com.unicartagena.edu.co.Empleo_Spring.Service.EmpleoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empleos")
public class EmpleoController {

    @Autowired
    private EmpleoService empleoService;

    // ==================== LISTAR ====================
    @GetMapping
    public String listar(Model model) {
        List<Empleo> empleos = empleoService.listarTodos();
        model.addAttribute("empleos", empleos);
        model.addAttribute("titulo", "Lista de Empleos");
        return "empleos/lista";
    }

    // ==================== CREAR ====================
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("empleo", new Empleo());
        model.addAttribute("titulo", "Registrar Nuevo Empleo");
        return "empleos/formulario";
    }

    // ==================== GUARDAR ====================
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Empleo empleo, RedirectAttributes flash) {
        empleoService.guardar(empleo);
        flash.addFlashAttribute("exito", "Empleo guardado correctamente.");
        return "redirect:/empleos";
    }

    // ==================== EDITAR ====================
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Empleo> empleo = empleoService.buscarPorId(id);
        if (empleo.isEmpty()) {
            return "redirect:/empleos";
        }
        model.addAttribute("empleo", empleo.get());
        model.addAttribute("titulo", "Editar Empleo");
        return "empleos/formulario";
    }

    // ==================== ELIMINAR ====================
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        empleoService.eliminar(id);
        flash.addFlashAttribute("exito", "Empleo eliminado correctamente.");
        return "redirect:/empleos";
    }

    // ==================== REPORTE 1: Por Categoría ====================
    @GetMapping("/reporte/categoria")
    public String reportePorCategoria(@RequestParam(required = false) String categoria, Model model) {
        if (categoria != null && !categoria.isEmpty()) {
            model.addAttribute("empleos", empleoService.buscarPorCategoria(categoria));
        }
        model.addAttribute("categoriaBuscada", categoria);
        model.addAttribute("titulo", "Reporte: Empleos por Categoría");
        return "empleos/reporte_categoria";
    }

    // ==================== REPORTE 2: Por Empresa ====================
    @GetMapping("/reporte/empresa")
    public String reportePorEmpresa(@RequestParam(required = false) String empresa, Model model) {
        if (empresa != null && !empresa.isEmpty()) {
            model.addAttribute("empleos", empleoService.buscarPorEmpresa(empresa));
        }
        model.addAttribute("empresaBuscada", empresa);
        model.addAttribute("titulo", "Reporte: Empleos por Empresa");
        return "empleos/reporte_empresa";
    }
    // ==================== VER POR ID ====================
    @GetMapping("/ver/{id}")
    public String verPorId(@PathVariable Long id, Model model) {
        Optional<Empleo> empleo = empleoService.buscarPorId(id);
        if (empleo.isEmpty()) {
            return "redirect:/empleos";
        }
        model.addAttribute("empleo", empleo.get());
        model.addAttribute("titulo", "Detalle del Empleo");
        return "empleos/ver";
    }
}