package com.cesde.proyecta.dto;

import com.cesde.proyecta.model.enums.EstadoReto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class RetoResponseDTO {

    private UUID id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDateTime fechaLimiteRpta;
    private EstadoReto estadoActual;
    private UUID idEmpresa;
    private String nombreEmpresa;
    private UUID idCategoria;
    private String nombreCategoria;
    private UUID idPrioridad;
    private String nombrePrioridad;

    public RetoResponseDTO() {
    }

    public RetoResponseDTO(UUID id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                           LocalDateTime fechaLimiteRpta, EstadoReto estadoActual, UUID idEmpresa, String nombreEmpresa,
                           UUID idCategoria, String nombreCategoria, UUID idPrioridad, String nombrePrioridad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaLimiteRpta = fechaLimiteRpta;
        this.estadoActual = estadoActual;
        this.idEmpresa = idEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.idPrioridad = idPrioridad;
        this.nombrePrioridad = nombrePrioridad;
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

    public UUID getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(UUID idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public UUID getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(UUID idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public UUID getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(UUID idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public String getNombrePrioridad() {
        return nombrePrioridad;
    }

    public void setNombrePrioridad(String nombrePrioridad) {
        this.nombrePrioridad = nombrePrioridad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetoResponseDTO that = (RetoResponseDTO) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

