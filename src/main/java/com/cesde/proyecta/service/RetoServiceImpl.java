package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.EstadoRetoUpdateDTO;
import com.cesde.proyecta.dto.RetoRequestDTO;
import com.cesde.proyecta.dto.RetoResponseDTO;
import com.cesde.proyecta.exception.ConflictoNegocioException;
import com.cesde.proyecta.exception.RecursoNoEncontradoException;
import com.cesde.proyecta.model.entity.CategoriaAcademica;
import com.cesde.proyecta.model.entity.Empresa;
import com.cesde.proyecta.model.entity.Prioridad;
import com.cesde.proyecta.model.entity.Reto;
import com.cesde.proyecta.model.entity.Usuario;
import com.cesde.proyecta.model.enums.EstadoReto;
import com.cesde.proyecta.model.enums.RolUsuario;
import com.cesde.proyecta.repository.CategoriaAcademicaRepository;
import com.cesde.proyecta.repository.EmpresaRepository;
import com.cesde.proyecta.repository.PrioridadRepository;
import com.cesde.proyecta.repository.RetoRepository;
import com.cesde.proyecta.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class RetoServiceImpl implements RetoService {

    /**
     * Matriz declarativa de la Máquina de Estados Finita (FSM) para transiciones estándar.
     * Mapea: Estado Origen -> (Estado Destino -> Conjunto de Roles Autorizados).
     */
    private static final Map<EstadoReto, Map<EstadoReto, Set<RolUsuario>>> MATRIZ_TRANSICIONES = Map.of(
            EstadoReto.BORRADOR, Map.of(
                    EstadoReto.RADICADO, EnumSet.of(RolUsuario.REPRESENTANTE_EMPRESA)
            ),
            EstadoReto.RADICADO, Map.of(
                    EstadoReto.EN_REVISION, EnumSet.of(RolUsuario.ADMIN_UNIVERSIDAD, RolUsuario.COORDINADOR_ESCUELA)
            ),
            EstadoReto.EN_REVISION, Map.of(
                    EstadoReto.EN_CONVOCATORIA, EnumSet.of(RolUsuario.ADMIN_UNIVERSIDAD, RolUsuario.COORDINADOR_ESCUELA)
            ),
            EstadoReto.EN_CONVOCATORIA, Map.of(
                    EstadoReto.EN_DESARROLLO, EnumSet.of(RolUsuario.ADMIN_UNIVERSIDAD, RolUsuario.COORDINADOR_ESCUELA)
            ),
            EstadoReto.EN_DESARROLLO, Map.of(
                    EstadoReto.FINALIZADO, EnumSet.of(
                            RolUsuario.REPRESENTANTE_EMPRESA,
                            RolUsuario.DOCENTE,
                            RolUsuario.ADMIN_UNIVERSIDAD
                    ),
                    EstadoReto.APLAZADO, EnumSet.of(
                            RolUsuario.DOCENTE,
                            RolUsuario.COORDINADOR_ESCUELA
                    )
            ),
            EstadoReto.APLAZADO, Map.of(
                    EstadoReto.EN_DESARROLLO, EnumSet.of(RolUsuario.ADMIN_UNIVERSIDAD, RolUsuario.COORDINADOR_ESCUELA)
            )
    );

    /**
     * Roles autorizados para cancelar un reto (hacia CANCELADO desde cualquier estado).
     */
    private static final Set<RolUsuario> ROLES_PERMITIDOS_CANCELACION = EnumSet.of(
            RolUsuario.ADMIN_UNIVERSIDAD,
            RolUsuario.REPRESENTANTE_EMPRESA
    );

    private final RetoRepository retoRepository;
    private final EmpresaRepository empresaRepository;
    private final CategoriaAcademicaRepository categoriaRepository;
    private final PrioridadRepository prioridadRepository;
    private final UsuarioRepository usuarioRepository;

    public RetoServiceImpl(RetoRepository retoRepository,
                           EmpresaRepository empresaRepository,
                           CategoriaAcademicaRepository categoriaRepository,
                           PrioridadRepository prioridadRepository,
                           UsuarioRepository usuarioRepository) {
        this.retoRepository = retoRepository;
        this.empresaRepository = empresaRepository;
        this.categoriaRepository = categoriaRepository;
        this.prioridadRepository = prioridadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Lista los retos aplicando Control de Acceso Basado en Roles (RBAC):
     * - ADMIN_UNIVERSIDAD / COORDINADOR_ESCUELA (y DOCENTE): Devuelve todos los retos (`findAll()`).
     * - REPRESENTANTE_EMPRESA: Devuelve únicamente los retos de su empresa (`findByEmpresaId(idEmpresa)`).
     * - ESTUDIANTE: Devuelve únicamente los retos en estado `EN_CONVOCATORIA` (`findByEstadoActual(...)`).
     */
    @Override
    @Transactional(readOnly = true)
    public List<RetoResponseDTO> listar() {
        List<Reto> retos = obtenerRetosSegunRolAutenticado();
        List<RetoResponseDTO> respuesta = new ArrayList<>(retos.size());
        for (Reto reto : retos) {
            respuesta.add(mapearAResponseDTO(reto));
        }
        return respuesta;
    }

    private List<Reto> obtenerRetosSegunRolAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return retoRepository.findByEstadoActual(EstadoReto.EN_CONVOCATORIA);
        }

        String emailAutenticado = authentication.getName();
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(emailAutenticado);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            RolUsuario rol = usuario.getRol();

            if (rol == RolUsuario.ADMIN_UNIVERSIDAD
                    || rol == RolUsuario.COORDINADOR_ESCUELA
                    || rol == RolUsuario.DOCENTE) {
                return retoRepository.findAll();
            }

            if (rol == RolUsuario.REPRESENTANTE_EMPRESA) {
                UUID idEmpresaUsuario = usuario.getIdEmpresa();
                if (idEmpresaUsuario == null) {
                    return Collections.emptyList();
                }
                return retoRepository.findByEmpresaId(idEmpresaUsuario);
            }

            if (rol == RolUsuario.ESTUDIANTE) {
                return retoRepository.findByEstadoActual(EstadoReto.EN_CONVOCATORIA);
            }
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String auth = authority.getAuthority();
            if ("ROLE_ADMIN_UNIVERSIDAD".equals(auth)
                    || "ADMIN_UNIVERSIDAD".equals(auth)
                    || "ROLE_COORDINADOR_ESCUELA".equals(auth)
                    || "COORDINADOR_ESCUELA".equals(auth)) {
                return retoRepository.findAll();
            }
        }

        return retoRepository.findByEstadoActual(EstadoReto.EN_CONVOCATORIA);
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
    public RetoResponseDTO actualizarEstado(UUID id, EstadoRetoUpdateDTO dto) {
        Reto retoExistente = buscarEntidadPorId(id);
        EstadoReto estadoActual = retoExistente.getEstadoActual();
        EstadoReto nuevoEstado = dto.getEstadoActual();

        RolUsuario rolUsuario = obtenerRolUsuarioAutenticado();

        // Validar la transición en la Máquina de Estados Finita (FSM) + RBAC
        validarTransicionEstado(estadoActual, nuevoEstado, rolUsuario);

        retoExistente.setEstadoActual(nuevoEstado);
        Reto actualizado = retoRepository.save(retoExistente);
        return mapearAResponseDTO(actualizado);
    }

    /**
     * Evalúa estrictamente la matriz de transiciones de la Máquina de Estados Finita (FSM)
     * según el estado actual del reto, el estado destino solicitado y el rol del usuario autenticado.
     */
    private void validarTransicionEstado(EstadoReto actual, EstadoReto nuevo, RolUsuario rol) {
        if (rol == null) {
            throw new ConflictoNegocioException(
                    "No se pudo verificar el rol del usuario autenticado para cambiar el estado del reto."
            );
        }

        if (actual == nuevo) {
            throw new ConflictoNegocioException(
                    String.format("El reto ya se encuentra en el estado %s.", actual)
            );
        }

        // Regla global: Hacia CANCELADO (desde cualquier estado) -> Solo ADMIN_UNIVERSIDAD o REPRESENTANTE_EMPRESA
        if (nuevo == EstadoReto.CANCELADO) {
            if (!ROLES_PERMITIDOS_CANCELACION.contains(rol)) {
                throw new ConflictoNegocioException(
                        String.format(
                                "Transición de estado no permitida para su rol (%s): solo ADMIN_UNIVERSIDAD o REPRESENTANTE_EMPRESA pueden cancelar un reto.",
                                rol
                        )
                );
            }
            return;
        }

        // Evaluar si existe transición válida desde el estado actual hacia el nuevo estado
        Map<EstadoReto, Set<RolUsuario>> transicionesDesdeActual = MATRIZ_TRANSICIONES.get(actual);
        if (transicionesDesdeActual == null || !transicionesDesdeActual.containsKey(nuevo)) {
            throw new ConflictoNegocioException(
                    String.format(
                            "Transición de estado inválida: no se permite pasar de %s a %s.",
                            actual,
                            nuevo
                    )
            );
        }

        // Evaluar si el rol del usuario tiene permiso para ejecutar dicha transición
        Set<RolUsuario> rolesAutorizados = transicionesDesdeActual.get(nuevo);
        if (!rolesAutorizados.contains(rol)) {
            throw new ConflictoNegocioException(
                    String.format(
                            "Transición de estado no permitida para su rol (%s): no tiene permisos para pasar de %s a %s.",
                            rol,
                            actual,
                            nuevo
                    )
            );
        }
    }

    /**
     * Extrae el RolUsuario del usuario autenticado desde el SecurityContextHolder,
     * consultando primero la base de datos por email y usando las authorities del JWT como respaldo.
     */
    private RolUsuario obtenerRolUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String emailAutenticado = authentication.getName();
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(emailAutenticado);
        if (usuarioOpt.isPresent()) {
            return usuarioOpt.get().getRol();
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String rawRole = authority.getAuthority();
            String cleanRole = rawRole.startsWith("ROLE_") ? rawRole.substring(5) : rawRole;
            try {
                return RolUsuario.valueOf(cleanRole);
            } catch (IllegalArgumentException ignored) {
                // Ignorar authorities que no correspondan al enum RolUsuario
            }
        }

        return null;
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
