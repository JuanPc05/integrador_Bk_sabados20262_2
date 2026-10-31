package com.cesde.proyecta.repository;

import com.cesde.proyecta.model.entity.Prioridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrioridadRepository extends JpaRepository<Prioridad, UUID> {

    boolean existsByNombre(String nombre);
}

