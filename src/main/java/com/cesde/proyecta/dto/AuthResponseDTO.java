package com.cesde.proyecta.dto;

public class AuthResponseDTO {

    private String accessToken;
    private String tokenType = "Bearer";
    private UsuarioResponseDTO usuario;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String accessToken, UsuarioResponseDTO usuario) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
        this.usuario = usuario;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }
}

