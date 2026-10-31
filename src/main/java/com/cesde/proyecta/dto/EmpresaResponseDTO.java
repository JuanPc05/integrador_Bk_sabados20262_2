package com.cesde.proyecta.dto;

import com.cesde.proyecta.model.enums.SectorEconomico;

import java.util.Objects;
import java.util.UUID;

public class EmpresaResponseDTO {

    private UUID id;
    private String nit;
    private String nombre;
    private SectorEconomico sector;
    private String contactoNombre;
    private String correo;
    private String telefono;
    private Boolean activa;

    public EmpresaResponseDTO() {
    }

    public EmpresaResponseDTO(UUID id, String nit, String nombre, SectorEconomico sector,
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
        EmpresaResponseDTO that = (EmpresaResponseDTO) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

