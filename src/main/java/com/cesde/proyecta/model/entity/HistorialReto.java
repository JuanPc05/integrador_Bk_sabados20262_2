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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

// Guarda cada cambio de estado de un reto (antes era la "Tabla Registros")
@Entity
@Table(name = "historial_reto")
public class HistorialReto {

    // dejo que la base genere el UUID para que nadie lo mande desde el front
    // lo guardo como VARCHAR(36) igual que Prioridad, para que después coincida con las otras tablas
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    private UUID id;

    // la fecha no se puede cambiar después de guardar el registro
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    // guardo el enum como texto para que no se dañe si cambio el orden de los valores
    // puede ser nulo porque el primer estado de un reto no tiene anterior
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior", length = 20)
    private EstadoReto estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_nuevo", nullable = false, length = 20)
    private EstadoReto estadoNuevo;

    @Column(columnDefinition = "TEXT")
    private String observacion;

    // por ahora guardo los ids como UUID simples, después los conecto con Reto y Usuario
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id_reto", length = 36, nullable = false)
    private UUID idReto;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id_usuario", length = 36, nullable = false)
    private UUID idUsuario;

    // JPA necesita un constructor vacío
    public HistorialReto() {
    }

    // pongo la fecha automáticamente justo antes de guardar
    @PrePersist
    void antesDeGuardar() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
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
}
