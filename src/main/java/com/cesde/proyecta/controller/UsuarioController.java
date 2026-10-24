package com.cesde.proyecta.controller;

import com.cesde.proyecta.model.dto.PerfilRequestDTO;
import com.cesde.proyecta.model.dto.UsuarioRequestDTO;
import com.cesde.proyecta.model.dto.UsuarioResponseDTO;
import com.cesde.proyecta.model.entity.Usuario;
import com.cesde.proyecta.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Inyección de dependencias por constructor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody UsuarioRequestDTO requestDTO) {
        // 1. Mapeo Manual: DTO de entrada -> Entidad
        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(requestDTO.getNombreCompleto());
        usuario.setEmail(requestDTO.getEmail());
        usuario.setPasswordHash(requestDTO.getPassword()); // El servicio deberá encriptarlo luego
        usuario.setRol(requestDTO.getRol());
        usuario.setIdEmpresa(requestDTO.getIdEmpresa());
        usuario.setActivo(true); // Por defecto un usuario nuevo está activo

        // 2. Ejecución de la lógica de negocio
        Usuario usuarioCreado = usuarioService.crearUsuario(usuario);

        // 3. Mapeo Manual: Entidad -> DTO de salida
        UsuarioResponseDTO responseDTO = mapearAResponseDTO(usuarioCreado);

        // 4. Retornar 201 Created
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();

        List<UsuarioResponseDTO> responseDTOs = usuarios.stream()
            .map(this::mapearAResponseDTO)
            .collect(Collectors.toList());

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@PathVariable UUID id) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorId(id);

        if (usuarioOpt.isPresent()) {
            return new ResponseEntity<>(mapearAResponseDTO(usuarioOpt.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable UUID id, @RequestBody UsuarioRequestDTO requestDTO) {
        Usuario datosParaActualizar = new Usuario();
        datosParaActualizar.setNombreCompleto(requestDTO.getNombreCompleto());
        datosParaActualizar.setEmail(requestDTO.getEmail());
        datosParaActualizar.setPasswordHash(requestDTO.getPassword());
        datosParaActualizar.setRol(requestDTO.getRol());
        datosParaActualizar.setIdEmpresa(requestDTO.getIdEmpresa());

        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, datosParaActualizar);
        return new ResponseEntity<>(mapearAResponseDTO(usuarioActualizado), HttpStatus.OK);
    }

    @PutMapping("/perfil/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarMiPerfil(@PathVariable UUID id, @RequestBody PerfilRequestDTO perfilDTO) {
        Usuario perfilActualizado = usuarioService.actualizarPerfil(id, perfilDTO);
        return new ResponseEntity<>(mapearAResponseDTO(perfilActualizado), HttpStatus.OK);
    }

    // Método auxiliar privado para centralizar el mapeo de Entidad a DTO de salida
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
