package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.HistorialRetoRequestDTO;
import com.cesde.proyecta.dto.HistorialRetoResponseDTO;

import java.util.List;
import java.util.UUID;

public interface HistorialRetoService {

    HistorialRetoResponseDTO crear(HistorialRetoRequestDTO solicitud);

    List<HistorialRetoResponseDTO> listar();

    HistorialRetoResponseDTO buscarPorId(UUID id);

    List<HistorialRetoResponseDTO> listarPorReto(UUID idReto);

    List<HistorialRetoResponseDTO> listarPorUsuario(UUID idUsuario);

    HistorialRetoResponseDTO actualizar(UUID id, HistorialRetoRequestDTO solicitud);

    void eliminar(UUID id);
}

