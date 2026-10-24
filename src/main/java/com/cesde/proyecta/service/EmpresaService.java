package com.cesde.proyecta.service;

import com.cesde.proyecta.model.dto.EmpresaRequestDTO;
import com.cesde.proyecta.model.dto.EmpresaResponseDTO;
import com.cesde.proyecta.model.entity.Empresa;
import com.cesde.proyecta.repository.EmpresaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> listar() {
        return empresaRepository.findAll().stream()
            .map(EmpresaResponseDTO::desde)
            .toList();
    }

    @Transactional(readOnly = true)
    public EmpresaResponseDTO obtenerPorId(UUID id) {
        return EmpresaResponseDTO.desde(buscar(id));
    }

    @Transactional
    public EmpresaResponseDTO crear(EmpresaRequestDTO request) {
        if (empresaRepository.existsByNit(request.nit())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "Ya existe una empresa con el NIT " + request.nit());
        }
        Empresa empresa = new Empresa();
        aplicarDatos(empresa, request);
        empresa.setActiva(true);
        return EmpresaResponseDTO.desde(empresaRepository.save(empresa));
    }

    @Transactional
    public EmpresaResponseDTO actualizar(UUID id, EmpresaRequestDTO request) {
        Empresa empresa = buscar(id);
        if (!empresa.getNit().equals(request.nit()) && empresaRepository.existsByNit(request.nit())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "Ya existe una empresa con el NIT " + request.nit());
        }
        aplicarDatos(empresa, request);
        return EmpresaResponseDTO.desde(empresaRepository.save(empresa));
    }

    @Transactional
    public void desactivar(UUID id) {
        Empresa empresa = buscar(id);
        empresa.setActiva(false);
        empresaRepository.save(empresa);
    }

    private Empresa buscar(UUID id) {
        return empresaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Empresa no encontrada"));
    }

    private void aplicarDatos(Empresa empresa, EmpresaRequestDTO r) {
        empresa.setNit (r.nit());
        empresa.setNombre (r.nombre());
        empresa.setSector (r.sector());
        empresa.setContactoNombre (r.contactoNombre());
        empresa.setCorreo (r.correo());
        empresa.setTelefono (r.telefono());
    }
}
