package com.cesde.proyecta.dto;

import com.cesde.proyecta.model.enums.SectorEconomico;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class EmpresaRequestDTO {

    @NotBlank(message = "El NIT es obligatorio.")
    @Size(max = 30, message = "El NIT no puede superar los 30 caracteres.")
    private String nit;

    @NotBlank(message = "El nombre de la empresa es obligatorio.")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres.")
    private String nombre;

    @NotNull(message = "El sector económico es obligatorio.")
    private SectorEconomico sector;

    @Size(max = 150, message = "El nombre de contacto no puede superar los 150 caracteres.")
    private String contactoNombre;

    @Email(message = "El correo electrónico debe tener un formato válido.")
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres.")
    private String correo;

    @Size(max = 30, message = "El teléfono no puede superar los 30 caracteres.")
    private String telefono;

    public EmpresaRequestDTO() {
    }

    public EmpresaRequestDTO(String nit, String nombre, SectorEconomico sector, String contactoNombre, String correo, String telefono) {
        this.nit = nit;
        this.nombre = nombre;
        this.sector = sector;
        this.contactoNombre = contactoNombre;
        this.correo = correo;
        this.telefono = telefono;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpresaRequestDTO that = (EmpresaRequestDTO) o;
        return Objects.equals(nit, that.nit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nit);
    }
}

