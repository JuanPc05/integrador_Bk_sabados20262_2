package com.cesde.proyecta.controller;

import com.cesde.proyecta.dto.RetoRequestDTO;
import com.cesde.proyecta.dto.RetoResponseDTO;
import com.cesde.proyecta.service.RetoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/retos")
public class RetoController {

    private final RetoService retoService;

    public RetoController(RetoService retoService) {
        this.retoService = retoService;
    }

    @GetMapping
    public ResponseEntity<List<RetoResponseDTO>> listar() {
        return ResponseEntity.ok(retoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetoResponseDTO> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(retoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<RetoResponseDTO> crear(@Valid @RequestBody RetoRequestDTO request) {
        RetoResponseDTO creado = retoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetoResponseDTO> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody RetoRequestDTO request) {
        return ResponseEntity.ok(retoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        retoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
