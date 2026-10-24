package com.cesde.proyecta.repository;

import com.cesde.proyecta.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    // Método derivado para buscar un usuario por su correo electrónico
    Optional<Usuario> findByEmail(String email);
}
