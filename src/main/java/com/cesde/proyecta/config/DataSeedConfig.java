package com.cesde.proyecta.config;

import com.cesde.proyecta.model.entity.Empresa;
import com.cesde.proyecta.model.entity.Usuario;
import com.cesde.proyecta.model.enums.RolUsuario;
import com.cesde.proyecta.model.enums.SectorEconomico;
import com.cesde.proyecta.repository.EmpresaRepository;
import com.cesde.proyecta.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * Configuración de sembrado inicial de datos (Data Seeding) para entornos de desarrollo y pruebas.
 * <p>
 * Aplica los principios SOLID:
 * <ul>
 *   <li><b>SRP (Responsabilidad Única):</b> Su única responsabilidad es poblar de forma idempotente
 *       las entidades base de prueba al arrancar el contexto de Spring Boot.</li>
 *   <li><b>DIP (Inversión de Dependencias):</b> Depende de abstracciones ({@link UsuarioRepository},
 *       {@link EmpresaRepository}, {@link PasswordEncoder}) inyectadas explícitamente por constructor.</li>
 * </ul>
 */
@Configuration
public class DataSeedConfig implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeedConfig.class);

    private static final String PASSWORD_BASE = "Cesde2026*";
    private static final String EMAIL_ADMIN = "admin.conecta@cesde.edu.co";
    private static final String EMAIL_REPRESENTANTE = "representante@techantioquia.com";
    private static final String NIT_EMPRESA_PRUEBA = "900123456-1";

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Inyección de dependencias por constructor (sin {@code @Autowired} en atributos).
     */
    public DataSeedConfig(
            UsuarioRepository usuarioRepository,
            EmpresaRepository empresaRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Verificación de idempotencia a nivel general y por registro
        if (usuarioRepository.count() > 0
                && usuarioRepository.existsByEmail(EMAIL_ADMIN)
                && usuarioRepository.existsByEmail(EMAIL_REPRESENTANTE)) {
            log.info("[DataSeed] Los datos iniciales de prueba ya existen. Se omite el sembrado.");
            return;
        }

        log.info("[DataSeed] Iniciando sembrado de datos de prueba para CESDE Conecta...");

        String passwordEncriptada = passwordEncoder.encode(PASSWORD_BASE);

        // 1. Sembrar Usuario Administrador (sin empresa asociada)
        if (!usuarioRepository.existsByEmail(EMAIL_ADMIN)) {
            Usuario admin = new Usuario();
            admin.setNombreCompleto("Administrador CESDE");
            admin.setEmail(EMAIL_ADMIN);
            admin.setPasswordHash(passwordEncriptada);
            admin.setRol(RolUsuario.ADMIN_UNIVERSIDAD);
            admin.setIdEmpresa(null);
            admin.setActivo(Boolean.TRUE);

            usuarioRepository.save(admin);
            log.info("[DataSeed] Usuario ADMIN_UNIVERSIDAD creado: {}", EMAIL_ADMIN);
        }

        // 2. Sembrar Empresa de Prueba (o recuperarla si ya existe por NIT)
        Empresa empresaGuardada = empresaRepository.findByNit(NIT_EMPRESA_PRUEBA)
                .orElseGet(() -> {
                    Empresa nuevaEmpresa = new Empresa();
                    nuevaEmpresa.setNit(NIT_EMPRESA_PRUEBA);
                    nuevaEmpresa.setNombre("Tech Antioquia SAS");
                    nuevaEmpresa.setSector(SectorEconomico.TECNOLOGIA_Y_SOFTWARE);
                    nuevaEmpresa.setContactoNombre("Representante Tech");
                    nuevaEmpresa.setCorreo(EMAIL_REPRESENTANTE);
                    nuevaEmpresa.setTelefono("3004567890");
                    nuevaEmpresa.setActiva(Boolean.TRUE);

                    Empresa guardada = empresaRepository.save(nuevaEmpresa);
                    log.info("[DataSeed] Empresa de prueba creada: {} (ID: {})", guardada.getNombre(), guardada.getId());
                    return guardada;
                });

        // 3. Sembrar Usuario Representante de Empresa vinculado al UUID de la empresa creada
        if (!usuarioRepository.existsByEmail(EMAIL_REPRESENTANTE)) {
            Usuario representante = new Usuario();
            representante.setNombreCompleto("Representante Tech");
            representante.setEmail(EMAIL_REPRESENTANTE);
            representante.setPasswordHash(passwordEncriptada);
            representante.setRol(RolUsuario.REPRESENTANTE_EMPRESA);
            representante.setIdEmpresa(empresaGuardada.getId());
            representante.setActivo(Boolean.TRUE);

            usuarioRepository.save(representante);
            log.info("[DataSeed] Usuario REPRESENTANTE_EMPRESA creado: {} -> Empresa ID: {}",
                    EMAIL_REPRESENTANTE, empresaGuardada.getId());
        }

        log.info("[DataSeed] Sembrado de datos completado exitosamente.");
    }
}

