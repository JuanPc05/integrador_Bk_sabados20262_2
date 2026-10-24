package com.cesde.proyecta.model.dto;

import com.cesde.proyecta.model.enums.EstadoReto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

// Lo que me manda el front al crear o actualizar un historial
// no lleva id ni fecha porque los pone el backend
public class HistorialRetoRequestDTO {

    @NotNull(message = "El id del reto es obligatorio")
    private UUID idReto;

    @NotNull(message = "El id del usuario es obligatorio")
    private UUID idUsuario;

    // puede venir vacío porque el primer estado de un reto no tiene anterior
    private EstadoReto estadoAnterior;

    @NotNull(message = "El estado nuevo es obligatorio")
    private EstadoReto estadoNuevo;

    @Size(max = 1000, message = "La observación no puede pasar de 1000 caracteres")
    private String observacion;

    public HistorialRetoRequestDTO() {
    }

    public HistorialRetoRequestDTO(UUID idReto, UUID idUsuario, EstadoReto estadoAnterior, EstadoReto estadoNuevo, String observacion) {
        this.idReto = idReto;
        this.idUsuario = idUsuario;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.observacion = observacion;
    }

    public UUID getIdReto() { return idReto; }
    public void setIdReto(UUID idReto) { this.idReto = idReto; }

    public UUID getIdUsuario() { return idUsuario; }
    public void setIdUsuario(UUID idUsuario) { this.idUsuario = idUsuario; }

    public EstadoReto getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(EstadoReto estadoAnterior) { this.estadoAnterior = estadoAnterior; }

    public EstadoReto getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(EstadoReto estadoNuevo) { this.estadoNuevo = estadoNuevo; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
