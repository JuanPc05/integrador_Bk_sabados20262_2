package com.cesde.proyecta.model.dto;

import com.cesde.proyecta.model.entity.Reto;
import com.cesde.proyecta.model.enums.EstadoReto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record RetoResponseDTO(
    UUID id,
    String nombre,
    String descripcion,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    LocalDateTime fechaLimiteRpta,
    EstadoReto estadoActual,
    UUID idEmpresa,
    String nombreEmpresa,
    UUID idCategoria,
    String nombreCategoria,
    UUID idPrioridad,
    String nombrePrioridad
) {
    // Patrón Factory Method para mapear automáticamente desde la entidad
    public static RetoResponseDTO desde(Reto r) {
        return new RetoResponseDTO(
            r.getId(), r.getNombre(), r.getDescripcion(),
            r.getFechaInicio(), r.getFechaFin(), r.getFechaLimiteRpta(),
            r.getEstadoActual(),
            r.getEmpresa().getId(), r.getEmpresa().getNombre(),
            r.getCategoria().getId(), r.getCategoria().getNombre(),
            r.getPrioridad().getId(), r.getPrioridad().getNombre()
        );
    }
}
