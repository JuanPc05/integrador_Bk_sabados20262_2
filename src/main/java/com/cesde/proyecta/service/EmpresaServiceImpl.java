package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.EmpresaRequestDTO;
import com.cesde.proyecta.dto.EmpresaResponseDTO;
import com.cesde.proyecta.exception.ConflictoNegocioException;
import com.cesde.proyecta.exception.RecursoNoEncontradoException;
import com.cesde.proyecta.model.entity.Empresa;
import com.cesde.proyecta.repository.EmpresaRepository;
import com.cesde.proyecta.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository) {
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
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
            throw new ConflictoNegocioException("El NIT ya está registrado: " + request.getNit());
        }

        if (request.getCorreo() != null && !request.getCorreo().isBlank()
                && empresaRepository.existsByCorreoIgnoreCase(request.getCorreo().trim())) {
            throw new ConflictoNegocioException("El correo corporativo ya está registrado: " + request.getCorreo());
        }

        Empresa nuevaEmpresa = new Empresa();
        aplicarDatos(nuevaEmpresa, request);
        nuevaEmpresa.setActiva(Boolean.TRUE);

        Empresa guardada = empresaRepository.save(nuevaEmpresa);

        // Extraer el email del usuario autenticado desde SecurityContextHolder y vincular la nueva empresa
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getName() != null) {
            String emailAutenticado = authentication.getName();
            usuarioRepository.findByEmail(emailAutenticado).ifPresent(usuario -> {
                usuario.setEmpresa(guardada);
                usuarioRepository.save(usuario);
            });
        }

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

