package com.cesde.proyecta.model.entity;

import com.cesde.proyecta.model.enums.SectorEconomico;
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
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nit", length = 30, nullable = false, unique = true)
    private String nit;

    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "sector", length = 50, nullable = false)
    private SectorEconomico sector;

    @Column(name = "contacto_nombre", length = 150)
    private String contactoNombre;

    @Column(name = "correo", length = 150)
    private String correo;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @Column(name = "activa", nullable = false)
    private Boolean activa = Boolean.TRUE;

    public Empresa() {
    }

    public Empresa(UUID id, String nit, String nombre, SectorEconomico sector,
                   String contactoNombre, String correo, String telefono, Boolean activa) {
        this.id = id;
        this.nit = nit;
        this.nombre = nombre;
        this.sector = sector;
        this.contactoNombre = contactoNombre;
        this.correo = correo;
        this.telefono = telefono;
        this.activa = activa;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public SectorEconomico getSector() {
        return sector;
    }

    public void setSector(SectorEconomico sector) {
        this.sector = sector;
    }

    public String getContactoNombre() {
        return contactoNombre;
    }

    public void setContactoNombre(String contactoNombre) {
        this.contactoNombre = contactoNombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return id != null && Objects.equals(id, empresa.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
