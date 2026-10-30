package com.cesde.proyecta.controller;

import com.cesde.proyecta.model.dto.EmpresaRequestDTO;
import com.cesde.proyecta.model.dto.EmpresaResponseDTO;
import com.cesde.proyecta.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public List<EmpresaResponseDTO> listar() {
        return empresaService.listar();
    }

    @GetMapping("/{id}")
    public EmpresaResponseDTO obtener(@PathVariable UUID id) {
        return empresaService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpresaResponseDTO crear(@Valid @RequestBody EmpresaRequestDTO request) {
        return empresaService.crear(request);
    }

    @PutMapping("/{id}")
    public EmpresaResponseDTO actualizar(@PathVariable UUID id, @Valid @RequestBody EmpresaRequestDTO request) {
        return empresaService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desactivar(@PathVariable UUID id) {
        empresaService.desactivar(id);
    }
}
