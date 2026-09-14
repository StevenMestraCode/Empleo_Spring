package com.unicartagena.edu.co.Empleo_Spring.Controller;

import com.unicartagena.edu.co.Empleo_Spring.Entity.Usuario;
import com.unicartagena.edu.co.Empleo_Spring.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ==================== LISTAR ====================
    @GetMapping
    public String listar(Model model) {
        List<Usuario> usuarios = usuarioService.listarTodos();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("titulo", "Lista de Usuarios");
        return "usuarios/lista"; // -> templates/usuarios/lista.html
    }

    // ==================== CREAR (Formulario) ====================
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Registrar Nuevo Usuario");
        return "usuarios/formulario";
    }

    // ==================== GUARDAR ====================
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario, RedirectAttributes flash) {
        // Validar correo duplicado solo si es nuevo
        if (usuario.getId() == null && usuarioService.existeCorreo(usuario.getCorreo())) {
            flash.addFlashAttribute("error", "El correo ya está registrado.");
            return "redirect:/usuarios/nuevo";
        }
        usuarioService.guardar(usuario);
        flash.addFlashAttribute("exito", "Usuario guardado correctamente.");
        return "redirect:/usuarios";
    }

    // ==================== EDITAR (Formulario) ====================
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        if (usuario.isEmpty()) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario.get());
        model.addAttribute("titulo", "Editar Usuario");
        return "usuarios/formulario";
    }

    // ==================== ELIMINAR ====================
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        usuarioService.eliminar(id);
        flash.addFlashAttribute("exito", "Usuario eliminado correctamente.");
        return "redirect:/usuarios";
    }

    // ==================== REPORTE 1: Por Rol ====================
    @GetMapping("/reporte/rol")
    public String reportePorRol(@RequestParam(required = false) String rol, Model model) {
        if (rol != null && !rol.isEmpty()) {
            model.addAttribute("usuarios", usuarioService.buscarPorRol(rol));
        }
        model.addAttribute("rolBuscado", rol);
        model.addAttribute("titulo", "Reporte: Usuarios por Rol");
        return "usuarios/reporte_rol";
    }

    // ==================== REPORTE 2: Por Nombre ====================
    @GetMapping("/reporte/nombre")
    public String reportePorNombre(@RequestParam(required = false) String nombre, Model model) {
        if (nombre != null && !nombre.isEmpty()) {
            model.addAttribute("usuarios", usuarioService.buscarPorNombre(nombre));
        }
        model.addAttribute("nombreBuscado", nombre);
        model.addAttribute("titulo", "Reporte: Usuarios por Nombre");
        return "usuarios/reporte_nombre";
    }
}