package com.cesde.proyecta.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "prioridades")
public class Prioridad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    private String nombre;

    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    @Column(name = "dias_max_respuesta", nullable = false)
    private Integer diasMaxRespuesta;

    public Prioridad() {
    }

    public Prioridad(String nombre, Integer nivel, Integer diasMaxRespuesta) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.diasMaxRespuesta = diasMaxRespuesta;
    }

    public Prioridad(UUID id, String nombre, Integer nivel, Integer diasMaxRespuesta) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
        this.diasMaxRespuesta = diasMaxRespuesta;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getDiasMaxRespuesta() {
        return diasMaxRespuesta;
    }

    public void setDiasMaxRespuesta(Integer diasMaxRespuesta) {
        this.diasMaxRespuesta = diasMaxRespuesta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prioridad prioridad = (Prioridad) o;
        return id != null && Objects.equals(id, prioridad.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
