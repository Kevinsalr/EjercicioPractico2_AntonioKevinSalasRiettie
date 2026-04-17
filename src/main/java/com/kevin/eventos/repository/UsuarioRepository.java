package com.kevin.eventos.repository;

import com.kevin.eventos.domain.Usuario;
import com.kevin.eventos.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRol(Rol rol);

}