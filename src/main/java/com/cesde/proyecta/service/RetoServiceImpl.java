package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.RetoRequestDTO;
import com.cesde.proyecta.dto.RetoResponseDTO;
import com.cesde.proyecta.exception.ConflictoNegocioException;
import com.cesde.proyecta.exception.RecursoNoEncontradoException;
import com.cesde.proyecta.model.entity.CategoriaAcademica;
import com.cesde.proyecta.model.entity.Empresa;
import com.cesde.proyecta.model.entity.Prioridad;
import com.cesde.proyecta.model.entity.Reto;
import com.cesde.proyecta.model.enums.EstadoReto;
import com.cesde.proyecta.repository.CategoriaAcademicaRepository;
import com.cesde.proyecta.repository.EmpresaRepository;
import com.cesde.proyecta.repository.PrioridadRepository;
import com.cesde.proyecta.repository.RetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RetoServiceImpl implements RetoService {

    private final RetoRepository retoRepository;
    private final EmpresaRepository empresaRepository;
    private final CategoriaAcademicaRepository categoriaRepository;
    private final PrioridadRepository prioridadRepository;

    public RetoServiceImpl(RetoRepository retoRepository,
                           EmpresaRepository empresaRepository,
                           CategoriaAcademicaRepository categoriaRepository,
                           PrioridadRepository prioridadRepository) {
        this.retoRepository = retoRepository;
        this.empresaRepository = empresaRepository;
        this.categoriaRepository = categoriaRepository;
        this.prioridadRepository = prioridadRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RetoResponseDTO> listar() {
        List<Reto> retos = retoRepository.findAll();
        List<RetoResponseDTO> respuesta = new ArrayList<>(retos.size());
        for (Reto reto : retos) {
            respuesta.add(mapearAResponseDTO(reto));
        }
        return respuesta;
    }

    @Override
    @Transactional(readOnly = true)
    public RetoResponseDTO obtenerPorId(UUID id) {
        return mapearAResponseDTO(buscarEntidadPorId(id));
    }

    @Override
    @Transactional
    public RetoResponseDTO crear(RetoRequestDTO request) {
        validarFechas(request);

        Empresa empresa = buscarEmpresa(request.getIdEmpresa());
        CategoriaAcademica categoria = buscarCategoria(request.getIdCategoria());
        Prioridad prioridad = buscarPrioridad(request.getIdPrioridad());

        Reto nuevoReto = new Reto();
        nuevoReto.setNombre(request.getNombre());
        nuevoReto.setDescripcion(request.getDescripcion());
        nuevoReto.setFechaInicio(request.getFechaInicio());
        nuevoReto.setFechaFin(request.getFechaFin());
        nuevoReto.setFechaLimiteRpta(request.getFechaLimiteRpta());
        nuevoReto.setEstadoActual(request.getEstadoActual() != null ? request.getEstadoActual() : EstadoReto.RADICADO);
        nuevoReto.setEmpresa(empresa);
        nuevoReto.setCategoria(categoria);
        nuevoReto.setPrioridad(prioridad);

        Reto guardado = retoRepository.save(nuevoReto);
        return mapearAResponseDTO(guardado);
    }

    @Override
    @Transactional
    public RetoResponseDTO actualizar(UUID id, RetoRequestDTO request) {
        validarFechas(request);

        Reto retoExistente = buscarEntidadPorId(id);
        Empresa empresa = buscarEmpresa(request.getIdEmpresa());
        CategoriaAcademica categoria = buscarCategoria(request.getIdCategoria());
        Prioridad prioridad = buscarPrioridad(request.getIdPrioridad());

        retoExistente.setNombre(request.getNombre());
        retoExistente.setDescripcion(request.getDescripcion());
        retoExistente.setFechaInicio(request.getFechaInicio());
        retoExistente.setFechaFin(request.getFechaFin());
        retoExistente.setFechaLimiteRpta(request.getFechaLimiteRpta());
        if (request.getEstadoActual() != null) {
            retoExistente.setEstadoActual(request.getEstadoActual());
        }
        retoExistente.setEmpresa(empresa);
        retoExistente.setCategoria(categoria);
        retoExistente.setPrioridad(prioridad);

        Reto actualizado = retoRepository.save(retoExistente);
        return mapearAResponseDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        Reto reto = buscarEntidadPorId(id);
        retoRepository.delete(reto);
    }

    private void validarFechas(RetoRequestDTO request) {
        if (request.getFechaFin().isBefore(request.getFechaInicio())) {
            throw new ConflictoNegocioException("La fecha de fin debe ser igual o posterior a la fecha de inicio.");
        }
    }

    private Reto buscarEntidadPorId(UUID id) {
        return retoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el reto con ID: " + id));
    }

    private Empresa buscarEmpresa(UUID idEmpresa) {
        return empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la empresa con ID: " + idEmpresa));
    }

    private CategoriaAcademica buscarCategoria(UUID idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la categoría académica con ID: " + idCategoria));
    }

    private Prioridad buscarPrioridad(UUID idPrioridad) {
        return prioridadRepository.findById(idPrioridad)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la prioridad con ID: " + idPrioridad));
    }

    private RetoResponseDTO mapearAResponseDTO(Reto reto) {
        return new RetoResponseDTO(
                reto.getId(),
                reto.getNombre(),
                reto.getDescripcion(),
                reto.getFechaInicio(),
                reto.getFechaFin(),
                reto.getFechaLimiteRpta(),
                reto.getEstadoActual(),
                reto.getEmpresa().getId(),
                reto.getEmpresa().getNombre(),
                reto.getCategoria().getId(),
                reto.getCategoria().getNombre(),
                reto.getPrioridad().getId(),
                reto.getPrioridad().getNombre()
        );
    }
}

