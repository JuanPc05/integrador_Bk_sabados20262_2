package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.DashboardStatsDTO;
import com.cesde.proyecta.model.enums.EstadoReto;
import com.cesde.proyecta.repository.RetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final List<EstadoReto> ESTADOS_ACTIVOS = List.of(
            EstadoReto.RADICADO,
            EstadoReto.EN_REVISION,
            EstadoReto.EN_CONVOCATORIA,
            EstadoReto.ASIGNADO,
            EstadoReto.EN_DESARROLLO
    );

    private static final List<EstadoReto> ESTADOS_EN_DESARROLLO = List.of(
            EstadoReto.ASIGNADO,
            EstadoReto.EN_DESARROLLO
    );

    private final RetoRepository retoRepository;

    public DashboardServiceImpl(RetoRepository retoRepository) {
        this.retoRepository = retoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDTO obtenerEstadisticas(UUID idEmpresa) {
        long totalRetos;
        long totalActivos;
        long enDesarrollo;
        long completados;

        if (idEmpresa != null) {
            totalRetos = retoRepository.countByEmpresaId(idEmpresa);
            totalActivos = retoRepository.countByEmpresaIdAndEstadoActualIn(idEmpresa, ESTADOS_ACTIVOS);
            enDesarrollo = retoRepository.countByEmpresaIdAndEstadoActualIn(idEmpresa, ESTADOS_EN_DESARROLLO);
            completados = retoRepository.countByEmpresaIdAndEstadoActual(idEmpresa, EstadoReto.FINALIZADO);
        } else {
            totalRetos = retoRepository.count();
            totalActivos = retoRepository.countByEstadoActualIn(ESTADOS_ACTIVOS);
            enDesarrollo = retoRepository.countByEstadoActualIn(ESTADOS_EN_DESARROLLO);
            completados = retoRepository.countByEstadoActual(EstadoReto.FINALIZADO);
        }

        double tasaExito = totalRetos > 0
                ? Math.round(((double) completados / totalRetos) * 1000.0) / 10.0
                : 0.0;

        int equiposPostulados = (int) enDesarrollo;

        return new DashboardStatsDTO(
                (int) totalActivos,
                equiposPostulados,
                (int) enDesarrollo,
                (int) completados,
                tasaExito
        );
    }
}

