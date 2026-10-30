package com.cesde.proyecta.model.entity;

import com.cesde.proyecta.model.enums.EstadoReto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "retos")
public class Reto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "fecha_limite_rpta", nullable = false)
    private LocalDateTime fechaLimiteRpta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_actual", length = 50, nullable = false)
    private EstadoReto estadoActual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaAcademica categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prioridad", nullable = false)
    private Prioridad prioridad;

    public Reto() {
    }

    public Reto(UUID id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                LocalDateTime fechaLimiteRpta, EstadoReto estadoActual, Empresa empresa,
                CategoriaAcademica categoria, Prioridad prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaLimiteRpta = fechaLimiteRpta;
        this.estadoActual = estadoActual;
        this.empresa = empresa;
        this.categoria = categoria;
        this.prioridad = prioridad;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDateTime getFechaLimiteRpta() {
        return fechaLimiteRpta;
    }

    public void setFechaLimiteRpta(LocalDateTime fechaLimiteRpta) {
        this.fechaLimiteRpta = fechaLimiteRpta;
    }

    public EstadoReto getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(EstadoReto estadoActual) {
        this.estadoActual = estadoActual;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public CategoriaAcademica getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAcademica categoria) {
        this.categoria = categoria;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reto reto = (Reto) o;
        return id != null && Objects.equals(id, reto.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
