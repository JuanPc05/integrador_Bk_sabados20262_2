package com.cesde.proyecta.model.enums;

public enum EstadoReto {
    BORRADOR,
    RADICADO,
    EN_REVISION,
    EN_CONVOCATORIA, // Equivale a APROBADO y abierto a grupos
    ASIGNADO,
    EN_DESARROLLO,   // Equivale a EN_EJECUCION en el frontend
    FINALIZADO,
    RECHAZADO
}
