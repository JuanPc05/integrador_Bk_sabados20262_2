package com.cesde.proyecta.controller;

import com.cesde.proyecta.dto.DashboardStatsDTO;
import com.cesde.proyecta.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> obtenerEstadisticas(
            @RequestParam(name = "idEmpresa", required = false) UUID idEmpresa) {
        return ResponseEntity.ok(dashboardService.obtenerEstadisticas(idEmpresa));
    }
}

