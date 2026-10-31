package com.cesde.proyecta.dto;

import com.cesde.proyecta.model.enums.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre completo es obligatorio.")
    @Size(max = 150, message = "El nombre completo no puede superar los 150 caracteres.")
    private String nombreCompleto;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico debe tener un formato válido.")
    @Size(max = 100, message = "El correo electrónico no puede superar los 100 caracteres.")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres.")
    private String password;

    @NotNull(message = "El rol del usuario es obligatorio.")
    private RolUsuario rol;

    private UUID idEmpresa;

    private Boolean activo;

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(String nombreCompleto, String email, String password, RolUsuario rol, UUID idEmpresa, Boolean activo) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.idEmpresa = idEmpresa;
        this.activo = activo;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

