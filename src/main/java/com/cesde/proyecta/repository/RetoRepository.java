package com.cesde.proyecta.repository;

import com.cesde.proyecta.model.entity.Reto;
import com.cesde.proyecta.model.enums.EstadoReto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RetoRepository extends JpaRepository<Reto, UUID> {

    // 1. Buscar retos por su estado actual
    List<Reto> findByEstadoActual(EstadoReto estadoActual);

    // 2. Buscar todos los retos que pertenecen a una empresa específica
    List<Reto> findByEmpresaId(UUID idEmpresa);

    // 3. Buscar retos por categoría
    List<Reto> findByCategoriaId(UUID idCategoria);
}
