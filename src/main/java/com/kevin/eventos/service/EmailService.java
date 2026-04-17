package com.kevin.eventos.service;

public interface EmailService {

    void enviarCorreo(String para, String asunto, String contenido);

}