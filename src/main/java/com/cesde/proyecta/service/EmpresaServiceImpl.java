package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.EmpresaRequestDTO;
import com.cesde.proyecta.dto.EmpresaResponseDTO;
import com.cesde.proyecta.exception.ConflictoNegocioException;
import com.cesde.proyecta.exception.RecursoNoEncontradoException;
import com.cesde.proyecta.model.entity.Empresa;
import com.cesde.proyecta.repository.EmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> listar() {
        List<Empresa> empresasActivas = empresaRepository.findByActivaTrue();
        List<EmpresaResponseDTO> respuesta = new ArrayList<>(empresasActivas.size());
        for (Empresa empresa : empresasActivas) {
            respuesta.add(mapearAResponseDTO(empresa));
        }
        return respuesta;
    }

    @Override
    @Transactional(readOnly = true)
    public EmpresaResponseDTO obtenerPorId(UUID id) {
        return mapearAResponseDTO(buscarEntidadPorId(id));
    }

    @Override
    @Transactional
    public EmpresaResponseDTO crear(EmpresaRequestDTO request) {
        if (empresaRepository.existsByNit(request.getNit())) {
            throw new ConflictoNegocioException("Ya existe una empresa registrada con el NIT: " + request.getNit());
        }

        Empresa empresa = new Empresa();
        aplicarDatos(empresa, request);
        empresa.setActiva(Boolean.TRUE);

        Empresa guardada = empresaRepository.save(empresa);
        return mapearAResponseDTO(guardada);
    }

    @Override
    @Transactional
    public EmpresaResponseDTO actualizar(UUID id, EmpresaRequestDTO request) {
        Empresa empresa = buscarEntidadPorId(id);

        if (!empresa.getNit().equals(request.getNit()) && empresaRepository.existsByNit(request.getNit())) {
            throw new ConflictoNegocioException("Ya existe otra empresa registrada con el NIT: " + request.getNit());
        }

        aplicarDatos(empresa, request);
        Empresa actualizada = empresaRepository.save(empresa);
        return mapearAResponseDTO(actualizada);
    }

    @Override
    @Transactional
    public void desactivar(UUID id) {
        Empresa empresa = buscarEntidadPorId(id);
        empresa.setActiva(Boolean.FALSE);
        empresaRepository.save(empresa);
    }

    private Empresa buscarEntidadPorId(UUID id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la empresa con ID: " + id));
    }

    private void aplicarDatos(Empresa empresa, EmpresaRequestDTO request) {
        empresa.setNit(request.getNit());
        empresa.setNombre(request.getNombre());
        empresa.setSector(request.getSector());
        empresa.setContactoNombre(request.getContactoNombre());
        empresa.setCorreo(request.getCorreo());
        empresa.setTelefono(request.getTelefono());
    }

    private EmpresaResponseDTO mapearAResponseDTO(Empresa empresa) {
        return new EmpresaResponseDTO(
                empresa.getId(),
                empresa.getNit(),
                empresa.getNombre(),
                empresa.getSector(),
                empresa.getContactoNombre(),
                empresa.getCorreo(),
                empresa.getTelefono(),
                empresa.getActiva()
        );
    }
}

