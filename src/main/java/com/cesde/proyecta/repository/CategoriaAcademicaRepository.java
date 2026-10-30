package com.cesde.proyecta.repository;

import com.cesde.proyecta.model.entity.CategoriaAcademica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoriaAcademicaRepository extends JpaRepository<CategoriaAcademica, UUID> {

    boolean existsByNombre(String nombre);
}

