package com.cesde.proyecta.dto;

import com.cesde.proyecta.model.enums.EstadoReto;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class EstadoRetoUpdateDTO {

    @NotNull(message = "El estado del reto es obligatorio.")
    @JsonAlias({"estado", "estado_actual"})
    private EstadoReto estadoActual;

    public EstadoRetoUpdateDTO() {
    }

    public EstadoRetoUpdateDTO(EstadoReto estadoActual) {
        this.estadoActual = estadoActual;
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
        EstadoRetoUpdateDTO that = (EstadoRetoUpdateDTO) o;
        return estadoActual == that.estadoActual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(estadoActual);
    }
}

