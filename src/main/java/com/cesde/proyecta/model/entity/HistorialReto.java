package com.cesde.proyecta.model.entity;

import com.cesde.proyecta.model.enums.EstadoReto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "historial_reto")
public class HistorialReto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior", length = 50)
    private EstadoReto estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_nuevo", nullable = false, length = 50)
    private EstadoReto estadoNuevo;

    @Column(columnDefinition = "TEXT")
    private String observacion;

    @Column(name = "id_reto", nullable = false)
    private UUID idReto;

    @Column(name = "id_usuario", nullable = false)
    private UUID idUsuario;

    public HistorialReto() {
    }

    public HistorialReto(UUID id, LocalDateTime fechaRegistro, EstadoReto estadoAnterior,
                         EstadoReto estadoNuevo, String observacion, UUID idReto, UUID idUsuario) {
        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.observacion = observacion;
        this.idReto = idReto;
        this.idUsuario = idUsuario;
    }

    @PrePersist
    void antesDeGuardar() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
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
        HistorialReto that = (HistorialReto) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
