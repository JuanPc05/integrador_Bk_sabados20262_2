package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.HistorialRetoRequestDTO;
import com.cesde.proyecta.dto.HistorialRetoResponseDTO;
import com.cesde.proyecta.exception.RecursoNoEncontradoException;
import com.cesde.proyecta.model.entity.HistorialReto;
import com.cesde.proyecta.repository.HistorialRetoRepository;
import com.cesde.proyecta.repository.RetoRepository;
import com.cesde.proyecta.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HistorialRetoServiceImpl implements HistorialRetoService {

    private final HistorialRetoRepository historialRetoRepository;
    private final RetoRepository retoRepository;
    private final UsuarioRepository usuarioRepository;

    public HistorialRetoServiceImpl(HistorialRetoRepository historialRetoRepository,
                                    RetoRepository retoRepository,
                                    UsuarioRepository usuarioRepository) {
        this.historialRetoRepository = historialRetoRepository;
        this.retoRepository = retoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public HistorialRetoResponseDTO crear(HistorialRetoRequestDTO solicitud) {
        validarExistenciaReferencias(solicitud.getIdReto(), solicitud.getIdUsuario());

        HistorialReto historial = new HistorialReto();
        copiarDatos(historial, solicitud);
        HistorialReto guardado = historialRetoRepository.save(historial);
        return mapearAResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialRetoResponseDTO> listar() {
        return mapearLista(historialRetoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public HistorialRetoResponseDTO buscarPorId(UUID id) {
        HistorialReto historial = buscarEntidadPorId(id);
        return mapearAResponseDTO(historial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialRetoResponseDTO> listarPorReto(UUID idReto) {
        if (!retoRepository.existsById(idReto)) {
            throw new RecursoNoEncontradoException("No se encontró el reto con ID: " + idReto);
        }
        return mapearLista(historialRetoRepository.findByIdRetoOrderByFechaRegistroAsc(idReto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialRetoResponseDTO> listarPorUsuario(UUID idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RecursoNoEncontradoException("No se encontró el usuario con ID: " + idUsuario);
        }
        return mapearLista(historialRetoRepository.findByIdUsuarioOrderByFechaRegistroDesc(idUsuario));
    }

    @Override
    @Transactional
    public HistorialRetoResponseDTO actualizar(UUID id, HistorialRetoRequestDTO solicitud) {
        HistorialReto historial = buscarEntidadPorId(id);
        validarExistenciaReferencias(solicitud.getIdReto(), solicitud.getIdUsuario());

        copiarDatos(historial, solicitud);
        HistorialReto actualizado = historialRetoRepository.save(historial);
        return mapearAResponseDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        HistorialReto historial = buscarEntidadPorId(id);
        historialRetoRepository.delete(historial);
    }

    private HistorialReto buscarEntidadPorId(UUID id) {
        return historialRetoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el registro de historial con ID: " + id));
    }

    private void validarExistenciaReferencias(UUID idReto, UUID idUsuario) {
        if (!retoRepository.existsById(idReto)) {
            throw new RecursoNoEncontradoException("No se encontró el reto con ID: " + idReto);
        }
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RecursoNoEncontradoException("No se encontró el usuario con ID: " + idUsuario);
        }
    }

    private void copiarDatos(HistorialReto historial, HistorialRetoRequestDTO solicitud) {
        historial.setIdReto(solicitud.getIdReto());
        historial.setIdUsuario(solicitud.getIdUsuario());
        historial.setEstadoAnterior(solicitud.getEstadoAnterior());
        historial.setEstadoNuevo(solicitud.getEstadoNuevo());
        historial.setObservacion(solicitud.getObservacion());
    }

    private HistorialRetoResponseDTO mapearAResponseDTO(HistorialReto historial) {
        return new HistorialRetoResponseDTO(
                historial.getId(),
                historial.getFechaRegistro(),
                historial.getEstadoAnterior(),
                historial.getEstadoNuevo(),
                historial.getObservacion(),
                historial.getIdReto(),
                historial.getIdUsuario()
        );
    }

    private List<HistorialRetoResponseDTO> mapearLista(List<HistorialReto> entidades) {
        List<HistorialRetoResponseDTO> respuesta = new ArrayList<>(entidades.size());
        for (HistorialReto entidad : entidades) {
            respuesta.add(mapearAResponseDTO(entidad));
        }
        return respuesta;
    }
}

