package com.unicartagena.edu.co.Empleo_Spring.Controller;

import com.unicartagena.edu.co.Empleo_Spring.Service.EmailService;
import com.unicartagena.edu.co.Empleo_Spring.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    // ==================== LOGIN ====================
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // ==================== RECUPERAR (Formulario) ====================
    @GetMapping("/recuperar")
    public String mostrarRecuperar() {
        return "auth/recuperar";
    }

    // ==================== RECUPERAR (Enviar correo) ====================
    @PostMapping("/recuperar/enviar")
    public String enviarRecuperacion(@RequestParam String correo, RedirectAttributes flash) {
        if (!usuarioService.existeCorreo(correo)) {
            flash.addFlashAttribute("error", "El correo no está registrado.");
            return "redirect:/recuperar";
        }
        String claveTemporal = usuarioService.generarClaveTemporal(correo);
        emailService.enviarCorreoRecuperacion(correo, claveTemporal);
        flash.addFlashAttribute("exito", "Se ha enviado una clave temporal a tu correo.");
        return "redirect:/login";
    }

    // ==================== INICIO ====================
    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("titulo", "Sistema de Gestión de Empleo");
        return "index";
    }
    @GetMapping("/healthz")
    @ResponseBody
    public String healthz() {
        return "OK";
    }
}