package com.kevin.eventos.service.impl;

import com.kevin.eventos.domain.Rol;
import com.kevin.eventos.repository.RolRepository;
import com.kevin.eventos.service.RolService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void eliminarRol(Long id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Optional<Rol> obtenerRolPorId(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public Optional<Rol> obtenerPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }
}