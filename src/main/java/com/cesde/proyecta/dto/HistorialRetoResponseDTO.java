package com.cesde.proyecta.dto;

import com.cesde.proyecta.model.enums.EstadoReto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class HistorialRetoResponseDTO {

    private UUID id;
    private LocalDateTime fechaRegistro;
    private EstadoReto estadoAnterior;
    private EstadoReto estadoNuevo;
    private String observacion;
    private UUID idReto;
    private UUID idUsuario;

    public HistorialRetoResponseDTO() {
    }

    public HistorialRetoResponseDTO(UUID id, LocalDateTime fechaRegistro, EstadoReto estadoAnterior,
                                    EstadoReto estadoNuevo, String observacion, UUID idReto, UUID idUsuario) {
        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.observacion = observacion;
        this.idReto = idReto;
        this.idUsuario = idUsuario;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistorialRetoResponseDTO that = (HistorialRetoResponseDTO) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

