package com.cesde.proyecta.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record RetoRequestDTO(
    @NotBlank(message = "El nombre del reto es obligatorio") String nombre,
    @NotBlank(message = "La descripción no puede estar vacía") String descripcion,
    @NotNull(message = "La fecha de inicio es obligatoria") LocalDate fechaInicio,
    @NotNull(message = "La fecha de fin es obligatoria") LocalDate fechaFin,
    @NotNull(message = "La fecha límite de respuesta es obligatoria") LocalDateTime fechaLimiteRpta,
    @NotNull(message = "El ID de la empresa es obligatorio") UUID idEmpresa,
    @NotNull(message = "El ID de la categoría es obligatorio") UUID idCategoria,
    @NotNull(message = "El ID de la prioridad es obligatorio") UUID idPrioridad
) {}
