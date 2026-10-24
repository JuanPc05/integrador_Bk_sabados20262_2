package com.cesde.proyecta.service;

import com.cesde.proyecta.model.dto.PerfilRequestDTO;
import com.cesde.proyecta.model.entity.Usuario;
import com.cesde.proyecta.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // Inyección de dependencias por constructor (Arquitectura Limpia, sin Lombok)
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya se encuentra registrado.");
        }

        // TODO: Encriptar contraseña con BCrypt antes de guardar
        // usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obtenerPorId(UUID id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario actualizarUsuario(UUID id, Usuario datosActualizados) {
        // 1. Verificar que el usuario exista
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("El usuario con el ID proporcionado no existe.");
        }

        Usuario usuarioExistente = usuarioOpt.get();

        // 2. Validar duplicidad si el administrador decidió cambiar el correo
        if (!usuarioExistente.getEmail().equals(datosActualizados.getEmail())) {
            Optional<Usuario> emailOcupado = usuarioRepository.findByEmail(datosActualizados.getEmail());
            if (emailOcupado.isPresent()) {
                throw new IllegalArgumentException("El nuevo correo electrónico ya está registrado por otro usuario.");
            }
        }

        // 3. Actualizar los campos mapeados desde el controlador
        usuarioExistente.setNombreCompleto(datosActualizados.getNombreCompleto());
        usuarioExistente.setEmail(datosActualizados.getEmail());
        usuarioExistente.setRol(datosActualizados.getRol());
        usuarioExistente.setIdEmpresa(datosActualizados.getIdEmpresa());

        // 4. Actualizar contraseña solo si fue provista
        if (datosActualizados.getPasswordHash() != null && !datosActualizados.getPasswordHash().isBlank()) {
            // TODO: Encriptar nueva contraseña con BCrypt
            usuarioExistente.setPasswordHash(datosActualizados.getPasswordHash());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public Usuario actualizarPerfil(UUID id, PerfilRequestDTO perfilDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();

        // Actualización segura del nombre
        if (perfilDTO.getNombreCompleto() != null && !perfilDTO.getNombreCompleto().isBlank()) {
            usuario.setNombreCompleto(perfilDTO.getNombreCompleto());
        }

        // Validación estricta para el cambio de contraseña
        if (perfilDTO.getPasswordNueva() != null && !perfilDTO.getPasswordNueva().isBlank()) {
            if (!usuario.getPasswordHash().equals(perfilDTO.getPasswordActual())) {
                throw new IllegalArgumentException("La contraseña actual es incorrecta.");
            }
            // TODO: Encriptar nueva contraseña con BCrypt
            usuario.setPasswordHash(perfilDTO.getPasswordNueva());
        }

        return usuarioRepository.save(usuario);
    }
}
