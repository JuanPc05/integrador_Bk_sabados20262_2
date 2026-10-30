package com.cesde.proyecta.dto;

import com.cesde.proyecta.model.enums.EstadoReto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class RetoRequestDTO {

    @NotBlank(message = "El nombre del reto es obligatorio.")
    @Size(max = 100, message = "El nombre del reto no puede superar los 100 caracteres.")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía.")
    private String descripcion;

    @NotNull(message = "La fecha de inicio es obligatoria.")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria.")
    private LocalDate fechaFin;

    @NotNull(message = "La fecha límite de respuesta es obligatoria.")
    private LocalDateTime fechaLimiteRpta;

    @NotNull(message = "El ID de la empresa es obligatorio.")
    private UUID idEmpresa;

    @NotNull(message = "El ID de la categoría es obligatorio.")
    private UUID idCategoria;

    @NotNull(message = "El ID de la prioridad es obligatorio.")
    private UUID idPrioridad;

    private EstadoReto estadoActual;

    public RetoRequestDTO() {
    }

    public RetoRequestDTO(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                          LocalDateTime fechaLimiteRpta, UUID idEmpresa, UUID idCategoria,
                          UUID idPrioridad, EstadoReto estadoActual) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaLimiteRpta = fechaLimiteRpta;
        this.idEmpresa = idEmpresa;
        this.idCategoria = idCategoria;
        this.idPrioridad = idPrioridad;
        this.estadoActual = estadoActual;
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

    public UUID getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(UUID idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public UUID getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(UUID idCategoria) {
        this.idCategoria = idCategoria;
    }

    public UUID getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(UUID idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public EstadoReto getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(EstadoReto estadoActual) {
        this.estadoActual = estadoActual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetoRequestDTO that = (RetoRequestDTO) o;
        return Objects.equals(nombre, that.nombre) &&
                Objects.equals(idEmpresa, that.idEmpresa) &&
                Objects.equals(fechaInicio, that.fechaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, idEmpresa, fechaInicio);
    }
}

