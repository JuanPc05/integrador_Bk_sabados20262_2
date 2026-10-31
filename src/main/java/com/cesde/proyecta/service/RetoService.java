package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.RetoRequestDTO;
import com.cesde.proyecta.dto.RetoResponseDTO;

import java.util.List;
import java.util.UUID;

public interface RetoService {

    List<RetoResponseDTO> listar();

    RetoResponseDTO obtenerPorId(UUID id);

    RetoResponseDTO crear(RetoRequestDTO request);

    RetoResponseDTO actualizar(UUID id, RetoRequestDTO request);

    void eliminar(UUID id);
}
