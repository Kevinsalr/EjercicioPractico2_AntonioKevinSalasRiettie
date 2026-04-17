package com.kevin.eventos.controller;

import com.kevin.eventos.domain.Rol;
import com.kevin.eventos.service.RolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    // ========================
    // LISTAR ROLES
    // ========================
    @GetMapping("/roles")
    public String listarRoles(Model model) {
        model.addAttribute("roles", rolService.listarRoles());
        return "roles/lista";
    }

    // ========================
    // FORMULARIO NUEVO
    // ========================
    @GetMapping("/roles/nuevo")
    public String nuevoRol(Model model) {
        model.addAttribute("rol", new Rol());
        return "roles/formulario";
    }

    // ========================
    // GUARDAR
    // ========================
    @PostMapping("/roles/guardar")
    public String guardarRol(@ModelAttribute Rol rol) {
        rolService.guardarRol(rol);
        return "redirect:/roles";
    }

    // ========================
    // ELIMINAR
    // ========================
    @GetMapping("/roles/eliminar/{id}")
    public String eliminarRol(@PathVariable Long id) {
        rolService.eliminarRol(id);
        return "redirect:/roles";
    }
}
