package com.cesde.proyecta.repository;

import com.cesde.proyecta.model.entity.Reto;
import com.cesde.proyecta.model.enums.EstadoReto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RetoRepository extends JpaRepository<Reto, UUID> {

    List<Reto> findByEstadoActual(EstadoReto estadoActual);

    List<Reto> findByEmpresaId(UUID idEmpresa);

    List<Reto> findByCategoriaId(UUID idCategoria);
}
