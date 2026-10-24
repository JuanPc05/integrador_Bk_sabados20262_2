package com.cesde.proyecta.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "prioridades")
public class Prioridad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    private String nombre;

    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    @Column(name = "dias_max_respuesta", nullable = false)
    private Integer diasMaxRespuesta;

    // Constructor vacío obligatorio para JPA
    public Prioridad() {
    }

    // Constructor útil para nuestro DataSeeder
    public Prioridad(String nombre, Integer nivel, Integer diasMaxRespuesta) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.diasMaxRespuesta = diasMaxRespuesta;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public Integer getDiasMaxRespuesta() { return diasMaxRespuesta; }
    public void setDiasMaxRespuesta(Integer diasMaxRespuesta) { this.diasMaxRespuesta = diasMaxRespuesta; }
}
