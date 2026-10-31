package com.cesde.proyecta.repository;

import com.cesde.proyecta.model.entity.HistorialReto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistorialRetoRepository extends JpaRepository<HistorialReto, UUID> {

    List<HistorialReto> findByIdRetoOrderByFechaRegistroAsc(UUID idReto);

    List<HistorialReto> findByIdUsuarioOrderByFechaRegistroDesc(UUID idUsuario);
}
