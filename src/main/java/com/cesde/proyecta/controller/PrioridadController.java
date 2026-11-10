package com.cesde.proyecta.controller;

import com.cesde.proyecta.model.entity.Prioridad;
import com.cesde.proyecta.repository.PrioridadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/prioridades")
public class PrioridadController {

    private final PrioridadRepository prioridadRepository;

    public PrioridadController(PrioridadRepository prioridadRepository) {
        this.prioridadRepository = prioridadRepository;
    }

    @GetMapping
    public ResponseEntity<List<Prioridad>> listar() {
        return ResponseEntity.ok(prioridadRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Prioridad> crear(@RequestBody Prioridad prioridad) {
        Prioridad guardada = prioridadRepository.save(prioridad);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }
}

