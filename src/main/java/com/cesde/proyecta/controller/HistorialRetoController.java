package com.cesde.proyecta.controller;

import com.cesde.proyecta.dto.HistorialRetoRequestDTO;
import com.cesde.proyecta.dto.HistorialRetoResponseDTO;
import com.cesde.proyecta.service.HistorialRetoService;
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
@RequestMapping("/api/historial-reto")
public class HistorialRetoController {

    private final HistorialRetoService historialRetoService;

    public HistorialRetoController(HistorialRetoService historialRetoService) {
        this.historialRetoService = historialRetoService;
    }

    @PostMapping
    public ResponseEntity<HistorialRetoResponseDTO> crear(@Valid @RequestBody HistorialRetoRequestDTO solicitud) {
        HistorialRetoResponseDTO creado = historialRetoService.crear(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<HistorialRetoResponseDTO>> listar() {
        return ResponseEntity.ok(historialRetoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialRetoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(historialRetoService.buscarPorId(id));
    }

    @GetMapping("/reto/{idReto}")
    public ResponseEntity<List<HistorialRetoResponseDTO>> listarPorReto(@PathVariable UUID idReto) {
        return ResponseEntity.ok(historialRetoService.listarPorReto(idReto));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<HistorialRetoResponseDTO>> listarPorUsuario(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(historialRetoService.listarPorUsuario(idUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialRetoResponseDTO> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody HistorialRetoRequestDTO solicitud) {
        return ResponseEntity.ok(historialRetoService.actualizar(id, solicitud));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        historialRetoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
