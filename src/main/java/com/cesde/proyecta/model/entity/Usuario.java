package com.cesde.proyecta.model.entity;

import com.cesde.proyecta.model.enums.RolUsuario;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolUsuario rol;

    @Column(name = "id_empresa")
    private UUID idEmpresa;

    @Column(nullable = false)
    private Boolean activo;

    // 1. Constructor vacío (Obligatorio para que Hibernate/JPA pueda instanciar la entidad)
    public Usuario() {
    }

    // 2. Constructor con todos los argumentos (Opcional, pero útil para instanciar objetos rápidamente)
    public Usuario(UUID id, String nombreCompleto, String email, String passwordHash, RolUsuario rol, UUID idEmpresa, Boolean activo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.idEmpresa = idEmpresa;
        this.activo = activo;
    }

    // 3. Getters y Setters tradicionales de Java

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public UUID getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(UUID idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
