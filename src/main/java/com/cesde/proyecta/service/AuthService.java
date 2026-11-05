package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.AuthResponseDTO;
import com.cesde.proyecta.dto.LoginRequestDTO;
import com.cesde.proyecta.dto.UsuarioRequestDTO;
import com.cesde.proyecta.dto.UsuarioResponseDTO;

public interface AuthService {

    AuthResponseDTO login(LoginRequestDTO requestDTO);

    UsuarioResponseDTO register(UsuarioRequestDTO requestDTO);
}

