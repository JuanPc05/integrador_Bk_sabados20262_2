package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.UsuarioRequestDTO;
import com.cesde.proyecta.dto.UsuarioResponseDTO;
import com.cesde.proyecta.exception.ConflictoNegocioException;
import com.cesde.proyecta.exception.RecursoNoEncontradoException;
import com.cesde.proyecta.model.entity.Usuario;
import com.cesde.proyecta.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO requestDTO) {
        if (usuarioRepository.existsByEmail(requestDTO.getEmail())) {
            throw new ConflictoNegocioException("El correo electrónico ya se encuentra registrado.");
        }

        Usuario nuevoUsuario = mapearAEntidad(requestDTO);
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return mapearAResponseDTO(usuarioGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerPorId(UUID id) {
        Usuario usuario = buscarEntidadPorId(id);
        return mapearAResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponseDTO> respuesta = new ArrayList<>(usuarios.size());
        for (Usuario usuario : usuarios) {
            respuesta.add(mapearAResponseDTO(usuario));
        }
        return respuesta;
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(UUID id, UsuarioRequestDTO requestDTO) {
        Usuario usuarioExistente = buscarEntidadPorId(id);

        Optional<Usuario> usuarioConMismoEmail = usuarioRepository.findByEmail(requestDTO.getEmail());
        if (usuarioConMismoEmail.isPresent() && !usuarioConMismoEmail.get().getId().equals(id)) {
            throw new ConflictoNegocioException("El correo electrónico ya está asociado a otro usuario.");
        }

        usuarioExistente.setNombreCompleto(requestDTO.getNombreCompleto());
        usuarioExistente.setEmail(requestDTO.getEmail());
        usuarioExistente.setPasswordHash(requestDTO.getPassword());
        usuarioExistente.setRol(requestDTO.getRol());
        usuarioExistente.setIdEmpresa(requestDTO.getIdEmpresa());
        if (requestDTO.getActivo() != null) {
            usuarioExistente.setActivo(requestDTO.getActivo());
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return mapearAResponseDTO(usuarioActualizado);
    }

    @Override
    @Transactional
    public void eliminarUsuario(UUID id) {
        Usuario usuario = buscarEntidadPorId(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario buscarEntidadPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el usuario con ID: " + id));
    }

    private Usuario mapearAEntidad(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setPasswordHash(dto.getPassword());
        usuario.setRol(dto.getRol());
        usuario.setIdEmpresa(dto.getIdEmpresa());
        usuario.setActivo(dto.getActivo() != null ? dto.getActivo() : Boolean.TRUE);
        return usuario;
    }

    private UsuarioResponseDTO mapearAResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombreCompleto(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getIdEmpresa(),
                usuario.getActivo()
        );
    }
}

