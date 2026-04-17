package com.kevin.eventos.controller;

import com.kevin.eventos.domain.Usuario;
import com.kevin.eventos.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/login";
    }

    // ========================
    // LISTAR
    // ========================
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "usuarios/lista";
    }

    // ========================
    // EDITAR
    // ========================
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "usuarios/formulario";
    }

    // ========================
    // ELIMINAR
    // ========================
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios";
    }
}