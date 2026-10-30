package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.UsuarioRequestDTO;
import com.cesde.proyecta.dto.UsuarioResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {

    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO requestDTO);

    UsuarioResponseDTO obtenerPorId(UUID id);

    List<UsuarioResponseDTO> listarTodos();

    UsuarioResponseDTO actualizarUsuario(UUID id, UsuarioRequestDTO requestDTO);

    void eliminarUsuario(UUID id);
}
