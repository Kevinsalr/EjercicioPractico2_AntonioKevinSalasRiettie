package com.kevin.eventos.service;

import com.kevin.eventos.domain.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UsuarioService extends UserDetailsService {

    List<Usuario> listarUsuarios();

    Usuario guardarUsuario(Usuario usuario);

    void eliminarUsuario(Long id);

    Optional<Usuario> obtenerUsuarioPorId(Long id);

    Optional<Usuario> obtenerPorEmail(String email);
}