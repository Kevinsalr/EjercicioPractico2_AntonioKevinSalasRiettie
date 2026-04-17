package com.kevin.eventos.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "evento")
public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private LocalDate fecha;

    private Integer capacidad;

    private boolean activo = true;

    public Evento() {
    }

    public Evento(String nombre, String descripcion, LocalDate fecha, Integer capacidad, boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.capacidad = capacidad;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}