package com.cesde.proyecta.model.entity;

import com.cesde.proyecta.model.enums.EscuelaResponsable;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "categorias")
public class CategoriaAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = 255, nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "area_responsable", length = 50, nullable = false)
    private EscuelaResponsable areaResponsable;

    public CategoriaAcademica() {
    }

    public CategoriaAcademica(String nombre, String descripcion, EscuelaResponsable areaResponsable) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.areaResponsable = areaResponsable;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EscuelaResponsable getAreaResponsable() { return areaResponsable; }
    public void setAreaResponsable(EscuelaResponsable areaResponsable) { this.areaResponsable = areaResponsable; }
}
