package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.EmpresaRequestDTO;
import com.cesde.proyecta.dto.EmpresaResponseDTO;

import java.util.List;
import java.util.UUID;

public interface EmpresaService {

    List<EmpresaResponseDTO> listar();

    EmpresaResponseDTO obtenerPorId(UUID id);

    EmpresaResponseDTO crear(EmpresaRequestDTO request);

    EmpresaResponseDTO actualizar(UUID id, EmpresaRequestDTO request);

    void desactivar(UUID id);
}
