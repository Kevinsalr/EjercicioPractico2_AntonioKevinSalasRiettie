package com.kevin.eventos.service.impl;

import com.kevin.eventos.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarCorreo(String para, String asunto, String contenido) {

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(para);
        mensaje.setSubject(asunto);
        mensaje.setText(contenido);

        mailSender.send(mensaje);
    }
}