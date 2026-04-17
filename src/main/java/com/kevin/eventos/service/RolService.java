package com.kevin.eventos.service;

import com.kevin.eventos.domain.Rol;
import java.util.List;
import java.util.Optional;

public interface RolService {

    List<Rol> listarRoles();

    Rol guardarRol(Rol rol);

    void eliminarRol(Long id);

    Optional<Rol> obtenerRolPorId(Long id);

    Optional<Rol> obtenerPorNombre(String nombre);
}