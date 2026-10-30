package com.cesde.proyecta.model.dto;

import com.cesde.proyecta.model.enums.SectorEconomico;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmpresaRequestDTO(
    @NotBlank(message = "El NIT es obligatorio") String nit,
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    @NotNull(message = "El sector es obligatorio") SectorEconomico sector,
    String contactoNombre,
    @Email(message = "Correo inválido") String correo,
    String telefono
) {
}
