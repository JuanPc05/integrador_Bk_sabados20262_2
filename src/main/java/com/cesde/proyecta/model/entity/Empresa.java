package com.cesde.proyecta.model.entity;

import com.cesde.proyecta.model.enums.SectorEconomico;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
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
    private Boolean activa = true;

    public Empresa() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public SectorEconomico getSector() { return sector; }
    public void setSector(SectorEconomico sector) { this.sector = sector; }

    public String getContactoNombre() { return contactoNombre; }
    public void setContactoNombre(String contactoNombre) { this.contactoNombre = contactoNombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
}
