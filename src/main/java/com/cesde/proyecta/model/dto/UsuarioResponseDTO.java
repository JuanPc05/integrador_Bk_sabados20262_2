package com.cesde.proyecta.model.dto;

import com.cesde.proyecta.model.enums.RolUsuario;
import java.util.UUID;

public class UsuarioResponseDTO {

    private UUID id;
    private String nombreCompleto;
    private String email;
    private RolUsuario rol;
    private UUID idEmpresa;
    private Boolean activo;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(UUID id, String nombreCompleto, String email, RolUsuario rol, UUID idEmpresa, Boolean activo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.rol = rol;
        this.idEmpresa = idEmpresa;
        this.activo = activo;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }

    public UUID getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(UUID idEmpresa) { this.idEmpresa = idEmpresa; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
