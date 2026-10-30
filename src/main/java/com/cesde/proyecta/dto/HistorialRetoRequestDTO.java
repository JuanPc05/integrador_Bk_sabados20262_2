package com.cesde.proyecta.dto;

import com.cesde.proyecta.model.enums.EstadoReto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.UUID;

public class HistorialRetoRequestDTO {

    @NotNull(message = "El ID del reto es obligatorio.")
    private UUID idReto;

    @NotNull(message = "El ID del usuario es obligatorio.")
    private UUID idUsuario;

    private EstadoReto estadoAnterior;

    @NotNull(message = "El estado nuevo es obligatorio.")
    private EstadoReto estadoNuevo;

    @Size(max = 1000, message = "La observación no puede superar los 1000 caracteres.")
    private String observacion;

    public HistorialRetoRequestDTO() {
    }

    public HistorialRetoRequestDTO(UUID idReto, UUID idUsuario, EstadoReto estadoAnterior,
                                   EstadoReto estadoNuevo, String observacion) {
        this.idReto = idReto;
        this.idUsuario = idUsuario;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.observacion = observacion;
    }

    public UUID getIdReto() {
        return idReto;
    }

    public void setIdReto(UUID idReto) {
        this.idReto = idReto;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public EstadoReto getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(EstadoReto estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public EstadoReto getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(EstadoReto estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistorialRetoRequestDTO that = (HistorialRetoRequestDTO) o;
        return Objects.equals(idReto, that.idReto) &&
                Objects.equals(idUsuario, that.idUsuario) &&
                estadoAnterior == that.estadoAnterior &&
                estadoNuevo == that.estadoNuevo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReto, idUsuario, estadoAnterior, estadoNuevo);
    }
}

