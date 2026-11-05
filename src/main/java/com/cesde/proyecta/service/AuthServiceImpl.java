package com.cesde.proyecta.service;

import com.cesde.proyecta.dto.AuthResponseDTO;
import com.cesde.proyecta.dto.LoginRequestDTO;
import com.cesde.proyecta.dto.UsuarioRequestDTO;
import com.cesde.proyecta.dto.UsuarioResponseDTO;
import com.cesde.proyecta.exception.ConflictoNegocioException;
import com.cesde.proyecta.exception.CredencialesInvalidasException;
import com.cesde.proyecta.model.entity.Usuario;
import com.cesde.proyecta.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
    private String jwtSecret;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrectos."));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new CredencialesInvalidasException("La cuenta de usuario se encuentra inactiva.");
        }

        if (!passwordEncoder.matches(requestDTO.getPassword(), usuario.getPasswordHash())) {
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos.");
        }

        String accessToken = generarTokenJwt(usuario);
        UsuarioResponseDTO usuarioResponse = mapearAResponseDTO(usuario);

        return new AuthResponseDTO(accessToken, usuarioResponse);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO register(UsuarioRequestDTO requestDTO) {
        if (usuarioRepository.existsByEmail(requestDTO.getEmail())) {
            throw new ConflictoNegocioException("El correo electrónico ya se encuentra registrado.");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreCompleto(requestDTO.getNombreCompleto());
        nuevoUsuario.setEmail(requestDTO.getEmail());
        nuevoUsuario.setPasswordHash(passwordEncoder.encode(requestDTO.getPassword()));
        nuevoUsuario.setRol(requestDTO.getRol());
        nuevoUsuario.setIdEmpresa(requestDTO.getIdEmpresa());
        nuevoUsuario.setActivo(requestDTO.getActivo() != null ? requestDTO.getActivo() : Boolean.TRUE);

        Usuario guardado = usuarioRepository.save(nuevoUsuario);
        return mapearAResponseDTO(guardado);
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

    /**
     * Genera un JWT estándar (HS256) usando javax.crypto nativo de Java 21 sin dependencias externas.
     */
    private String generarTokenJwt(Usuario usuario) {
        try {
            long now = Instant.now().getEpochSecond();
            long exp = now + 86400; // 24 horas

            String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
            String payloadJson = String.format(
                    "{\"sub\":\"%s\",\"id\":\"%s\",\"rol\":\"%s\",\"iat\":%d,\"exp\":%d}",
                    usuario.getEmail(),
                    usuario.getId(),
                    usuario.getRol().name(),
                    now,
                    exp
            );

            Base64.Encoder urlEncoder = Base64.getUrlEncoder().withoutPadding();
            String headerBase64 = urlEncoder.encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
            String payloadBase64 = urlEncoder.encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
            String unsignedToken = headerBase64 + "." + payloadBase64;

            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secretKey);
            String signatureBase64 = urlEncoder.encodeToString(
                    hmacSha256.doFinal(unsignedToken.getBytes(StandardCharsets.UTF_8))
            );

            return unsignedToken + "." + signatureBase64;
        } catch (Exception e) {
            throw new IllegalStateException("Error al firmar el token JWT", e);
        }
    }
}

