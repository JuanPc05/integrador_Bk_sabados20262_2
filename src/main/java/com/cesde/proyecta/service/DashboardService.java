package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.DashboardStatsDTO;

import java.util.UUID;

public interface DashboardService {

    DashboardStatsDTO obtenerEstadisticas(UUID idEmpresa);
}

