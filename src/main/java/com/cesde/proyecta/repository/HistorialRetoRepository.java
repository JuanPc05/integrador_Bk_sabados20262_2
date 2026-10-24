package com.cesde.proyecta.repository;

import com.cesde.proyecta.model.entity.HistorialReto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

// JpaRepository ya trae guardar, buscar por id, listar, actualizar y borrar
@Repository
public interface HistorialRetoRepository extends JpaRepository<HistorialReto, UUID> {

    // traigo la línea de tiempo de un reto, del cambio más viejo al más nuevo
    List<HistorialReto> findByIdRetoOrderByFechaRegistroAsc(UUID idReto);

    // traigo lo que ha registrado una persona, lo más reciente primero
    List<HistorialReto> findByIdUsuarioOrderByFechaRegistroDesc(UUID idUsuario);
}
