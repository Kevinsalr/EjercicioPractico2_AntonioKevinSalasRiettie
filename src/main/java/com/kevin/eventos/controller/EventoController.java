package com.kevin.eventos.controller;

import com.kevin.eventos.domain.Evento;
import com.kevin.eventos.repository.EventoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventoController {

    private final EventoRepository eventoRepository;

    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @GetMapping("/eventos")
    public String listarEventos(Model model) {
        model.addAttribute("eventos", eventoRepository.findAll());
        return "eventos/lista";
    }

    @GetMapping("/eventos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("evento", new Evento());
        return "eventos/formulario";
    }

    @PostMapping("/eventos/guardar")
    public String guardarEvento(@ModelAttribute Evento evento) {
        eventoRepository.save(evento);
        return "redirect:/eventos";
    }

    @GetMapping("/eventos/editar/{id}")
    public String editarEvento(@PathVariable Long id, Model model) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        model.addAttribute("evento", evento);
        return "eventos/formulario";
    }

    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id) {
        eventoRepository.deleteById(id);
        return "redirect:/eventos";
    }

    // ========================
    // CONSULTAS
    // ========================
    @GetMapping("/eventos/buscar")
    public String buscarEventos(@RequestParam(required = false) String nombre,
                                @RequestParam(required = false) Boolean activo,
                                Model model) {

        if (nombre != null && !nombre.isEmpty()) {
            model.addAttribute("eventos",
                eventoRepository.findByNombreContainingIgnoreCase(nombre));
        } else if (activo != null) {
            model.addAttribute("eventos",
                eventoRepository.findByActivo(activo));
        } else {
            model.addAttribute("eventos",
                eventoRepository.findAll());
        }

        model.addAttribute("totalActivos", eventoRepository.countByActivoTrue());

        return "eventos/consultas";
    }
}