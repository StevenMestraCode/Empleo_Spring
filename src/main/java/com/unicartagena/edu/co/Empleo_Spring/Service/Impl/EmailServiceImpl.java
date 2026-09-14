package com.unicartagena.edu.co.Empleo_Spring.Service.Impl;

import com.unicartagena.edu.co.Empleo_Spring.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.mail.remitente}")
    private String remitente;

    @Override
    public void enviarCorreoRecuperacion(String destinatario, String claveTemporal) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(remitente);
        mensaje.setTo(destinatario);
        mensaje.setSubject("Recuperación de contraseña - Sistema de Empleo");
        mensaje.setText(
                "Hola,\n\n" +
                        "Has solicitado recuperar tu contraseña.\n\n" +
                        "Tu nueva clave temporal es: " + claveTemporal + "\n\n" +
                        "Por seguridad, cambia esta contraseña al iniciar sesión.\n\n" +
                        "Si no solicitaste este cambio, ignora este correo.\n\n" +
                        "Atentamente,\n" +
                        "Equipo de Soporte"
        );
        mailSender.send(mensaje);
    }
}