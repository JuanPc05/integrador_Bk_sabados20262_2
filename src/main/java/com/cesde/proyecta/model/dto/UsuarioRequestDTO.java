package com.cesde.proyecta.model.dto;

import com.cesde.proyecta.model.enums.RolUsuario;
import java.util.UUID;

public class UsuarioRequestDTO {

    private String nombreCompleto;
    private String email;
    private String password;
    private RolUsuario rol;
    private UUID idEmpresa;

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(String nombreCompleto, String email, String password, RolUsuario rol, UUID idEmpresa) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.idEmpresa = idEmpresa;
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }

    public UUID getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(UUID idEmpresa) { this.idEmpresa = idEmpresa; }
}
