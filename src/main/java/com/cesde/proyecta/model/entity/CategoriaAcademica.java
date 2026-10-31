package com.cesde.proyecta.model.entity;

import com.cesde.proyecta.model.enums.EscuelaResponsable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "categorias")
public class CategoriaAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = 255, nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "area_responsable", length = 50, nullable = false)
    private EscuelaResponsable areaResponsable;

    @Column(name = "activo", nullable = false)
    private Boolean activo = Boolean.TRUE;

    public CategoriaAcademica() {
    }

    public CategoriaAcademica(String nombre, String descripcion, EscuelaResponsable areaResponsable) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.areaResponsable = areaResponsable;
        this.activo = Boolean.TRUE;
    }

    public CategoriaAcademica(UUID id, String nombre, String descripcion, EscuelaResponsable areaResponsable, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.areaResponsable = areaResponsable;
        this.activo = activo;
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

    public EscuelaResponsable getAreaResponsable() {
        return areaResponsable;
    }

    public void setAreaResponsable(EscuelaResponsable areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaAcademica that = (CategoriaAcademica) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
