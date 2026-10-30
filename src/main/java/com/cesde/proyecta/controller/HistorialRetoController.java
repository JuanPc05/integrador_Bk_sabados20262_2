package com.cesde.proyecta.controller;

import com.cesde.proyecta.model.dto.HistorialRetoRequestDTO;
import com.cesde.proyecta.model.dto.HistorialRetoResponseDTO;
import com.cesde.proyecta.model.entity.HistorialReto;
import com.cesde.proyecta.repository.HistorialRetoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

// Recibe las peticiones del front y devuelve JSON
// CrossOrigin deja que mi React (Vite en el puerto 5173) llame a esta API
@RestController
@RequestMapping("/api/historial-reto")
@CrossOrigin(origins = "http://localhost:5173")
public class HistorialRetoController {

    // TODO: cuando exista el service, cambio el repositorio por HistorialRetoService
    private final HistorialRetoRepository historialRetoRepository;

    public HistorialRetoController(HistorialRetoRepository historialRetoRepository) {
        this.historialRetoRepository = historialRetoRepository;
    }

    // crear: devuelve 201 y el historial ya guardado
    @PostMapping
    public ResponseEntity<HistorialRetoResponseDTO> crear(@Valid @RequestBody HistorialRetoRequestDTO solicitud) {
        HistorialReto historial = new HistorialReto();
        copiarDatos(historial, solicitud);
        HistorialReto guardado = historialRetoRepository.save(historial);
        return ResponseEntity.status(HttpStatus.CREATED).body(HistorialRetoResponseDTO.desde(guardado));
    }

    // listar todos
    @GetMapping
    public ResponseEntity<List<HistorialRetoResponseDTO>> listar() {
        List<HistorialRetoResponseDTO> lista = historialRetoRepository.findAll()
            .stream()
            .map(HistorialRetoResponseDTO::desde)
            .toList();
        return ResponseEntity.ok(lista);
    }

    // buscar por id: 404 si no existe
    @GetMapping("/{id}")
    public ResponseEntity<HistorialRetoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return historialRetoRepository.findById(id)
            .map(historial -> ResponseEntity.ok(HistorialRetoResponseDTO.desde(historial)))
            .orElse(ResponseEntity.notFound().build());
    }

    // la línea de tiempo de un reto
    @GetMapping("/reto/{idReto}")
    public ResponseEntity<List<HistorialRetoResponseDTO>> listarPorReto(@PathVariable UUID idReto) {
        List<HistorialRetoResponseDTO> lista = historialRetoRepository.findByIdRetoOrderByFechaRegistroAsc(idReto)
            .stream()
            .map(HistorialRetoResponseDTO::desde)
            .toList();
        return ResponseEntity.ok(lista);
    }

    // lo que ha registrado un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<HistorialRetoResponseDTO>> listarPorUsuario(@PathVariable UUID idUsuario) {
        List<HistorialRetoResponseDTO> lista = historialRetoRepository.findByIdUsuarioOrderByFechaRegistroDesc(idUsuario)
            .stream()
            .map(HistorialRetoResponseDTO::desde)
            .toList();
        return ResponseEntity.ok(lista);
    }

    // actualizar: 404 si no existe, la fecha de registro no cambia
    @PutMapping("/{id}")
    public ResponseEntity<HistorialRetoResponseDTO> actualizar(@PathVariable UUID id,
                                                               @Valid @RequestBody HistorialRetoRequestDTO solicitud) {
        return historialRetoRepository.findById(id)
            .map(historial -> {
                copiarDatos(historial, solicitud);
                HistorialReto actualizado = historialRetoRepository.save(historial);
                return ResponseEntity.ok(HistorialRetoResponseDTO.desde(actualizado));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // eliminar: 204 si se borró, 404 si no existe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        if (!historialRetoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        historialRetoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // paso los datos del DTO a la entidad en un solo lugar
    private void copiarDatos(HistorialReto historial, HistorialRetoRequestDTO solicitud) {
        historial.setIdReto(solicitud.getIdReto());
        historial.setIdUsuario(solicitud.getIdUsuario());
        historial.setEstadoAnterior(solicitud.getEstadoAnterior());
        historial.setEstadoNuevo(solicitud.getEstadoNuevo());
        historial.setObservacion(solicitud.getObservacion());
    }
}
