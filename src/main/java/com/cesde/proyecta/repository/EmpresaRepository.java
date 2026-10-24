package com.cesde.proyecta.repository;

import com.cesde.proyecta.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {

    Optional<Empresa> findByNit(String nit);

    boolean existsByNit(String nit);

    List<Empresa> findByActivaTrue();
}
