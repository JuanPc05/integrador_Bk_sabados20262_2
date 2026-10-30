package com.cesde.proyecta.service;

import com.cesde.proyecta.model.dto.RetoRequestDTO;
import com.cesde.proyecta.model.dto.RetoResponseDTO;
import com.cesde.proyecta.model.entity.CategoriaAcademica;
import com.cesde.proyecta.model.entity.Empresa;
import com.cesde.proyecta.model.entity.Prioridad;
import com.cesde.proyecta.model.entity.Reto;
import com.cesde.proyecta.model.enums.EstadoReto;
import com.cesde.proyecta.repository.CategoriaAcademicaRepository;
import com.cesde.proyecta.repository.EmpresaRepository;
import com.cesde.proyecta.repository.PrioridadRepository;
import com.cesde.proyecta.repository.RetoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class RetoService {

    private final RetoRepository retoRepository;
    private final EmpresaRepository empresaRepository;
    private final CategoriaAcademicaRepository categoriaRepository;
    private final PrioridadRepository prioridadRepository;

    // Inyección de dependencias por constructor (Estilo EmpresaService)
    public RetoService(RetoRepository retoRepository, EmpresaRepository empresaRepository,
                       CategoriaAcademicaRepository categoriaRepository, PrioridadRepository prioridadRepository) {
        this.retoRepository = retoRepository;
        this.empresaRepository = empresaRepository;
        this.categoriaRepository = categoriaRepository;
        this.prioridadRepository = prioridadRepository;
    }

    @Transactional(readOnly = true)
    public List<RetoResponseDTO> listar() {
        return retoRepository.findAll().stream()
            .map(RetoResponseDTO::desde)
            .toList();
    }

    @Transactional(readOnly = true)
    public RetoResponseDTO obtenerPorId(UUID id) {
        return RetoResponseDTO.desde(buscar(id));
    }

    @Transactional
    public RetoResponseDTO crear(RetoRequestDTO request) {
        // Regla de Negocio: Validar coherencia de fechas
        if (request.fechaFin().isBefore(request.fechaInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "La fecha fin debe ser posterior a la de inicio");
        }

        // Buscar entidades relacionadas (Llaves foráneas)
        Empresa empresa = empresaRepository.findById(request.idEmpresa())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa no encontrada"));

        CategoriaAcademica categoria = categoriaRepository.findById(request.idCategoria())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));

        Prioridad prioridad = prioridadRepository.findById(request.idPrioridad())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prioridad no encontrada"));

        // Ensamblar y guardar (Usando instanciación clásica y setters)
        Reto nuevoReto = new Reto();
        nuevoReto.setNombre(request.nombre());
        nuevoReto.setDescripcion(request.descripcion());
        nuevoReto.setFechaInicio(request.fechaInicio());
        nuevoReto.setFechaFin(request.fechaFin());
        nuevoReto.setFechaLimiteRpta(request.fechaLimiteRpta());
        nuevoReto.setEstadoActual(EstadoReto.RADICADO);
        nuevoReto.setEmpresa(empresa);
        nuevoReto.setCategoria(categoria);
        nuevoReto.setPrioridad(prioridad);

        return RetoResponseDTO.desde(retoRepository.save(nuevoReto));
    }

    // Método privado reutilizable
    private Reto buscar(UUID id) {
        return retoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Reto no encontrado"));
    }
}
