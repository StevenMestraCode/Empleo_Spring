package com.unicartagena.edu.co.Empleo_Spring.Service.Impl;

import com.unicartagena.edu.co.Empleo_Spring.Service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${resend.api.key}")
    private String apiKey;

    @Value("${resend.api.url}")
    private String apiUrl;

    @Value("${resend.from}")
    private String remitente;

    @Override
    public void enviarCorreoRecuperacion(String destinatario, String claveTemporal) {
        try {
            if (apiKey == null || apiKey.isEmpty()) {
                throw new Exception("La variable RESEND_API no está configurada");
            }

            String asunto = "Recuperación de contraseña - Sistema de Empleo";
            String cuerpo = "Hola,\n\n"
                    + "Has solicitado recuperar tu contraseña.\n\n"
                    + "Tu nueva clave temporal es: " + claveTemporal + "\n\n"
                    + "Por seguridad, cambia esta contraseña al iniciar sesión.\n\n"
                    + "Si no solicitaste este cambio, ignora este correo.\n\n"
                    + "Atentamente,\n"
                    + "Equipo de Soporte";

            // Escapar caracteres especiales del JSON
            String asuntoEscapado = asunto.replace("\"", "\\\"").replace("\n", "\\n");
            String cuerpoEscapado = cuerpo.replace("\"", "\\\"").replace("\n", "\\n");

            String json = String.format(
                    "{\"from\":\"%s\",\"to\":\"%s\",\"subject\":\"%s\",\"text\":\"%s\"}",
                    remitente, destinatario, asuntoEscapado, cuerpoEscapado
            );

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(15))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Error al enviar correo (código " + response.statusCode() + "): " + response.body());
            }

            System.out.println("Correo enviado a " + destinatario + " vía Resend");

        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}