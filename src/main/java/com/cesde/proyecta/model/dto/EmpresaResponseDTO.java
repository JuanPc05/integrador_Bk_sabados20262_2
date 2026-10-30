package com.cesde.proyecta.model.dto;

import com.cesde.proyecta.model.entity.Empresa;
import com.cesde.proyecta.model.enums.SectorEconomico;

import java.util.UUID;

public record EmpresaResponseDTO(
    UUID id,
    String nit,
    String nombre,
    SectorEconomico sector,
    String contactoNombre,
    String correo,
    String telefono,
    Boolean activa
) {
    public static EmpresaResponseDTO desde(Empresa e) {
        return new EmpresaResponseDTO(
            e.getId(), e.getNit(), e.getNombre(), e.getSector(),
            e.getContactoNombre(), e.getCorreo(), e.getTelefono(), e.getActiva());
    }
}
