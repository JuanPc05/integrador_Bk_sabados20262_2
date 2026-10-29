package com.cesde.proyecta.controller;

import com.cesde.proyecta.model.dto.RetoRequestDTO;
import com.cesde.proyecta.model.dto.RetoResponseDTO;
import com.cesde.proyecta.service.RetoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/retos")
public class RetoController {

    private final RetoService retoService;

    // Inyección de dependencias por constructor
    public RetoController(RetoService retoService) {
        this.retoService = retoService;
    }

    @GetMapping
    public List<RetoResponseDTO> listar() {
        return retoService.listar();
    }

    @GetMapping("/{id}")
    public RetoResponseDTO obtener(@PathVariable UUID id) {
        return retoService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetoResponseDTO crear(@Valid @RequestBody RetoRequestDTO request) {
        return retoService.crear(request);
    }
}
