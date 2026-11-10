package com.cesde.proyecta.controller;

import com.cesde.proyecta.model.entity.CategoriaAcademica;
import com.cesde.proyecta.repository.CategoriaAcademicaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaAcademicaRepository categoriaRepository;

    public CategoriaController(CategoriaAcademicaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaAcademica>> listar() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<CategoriaAcademica> crear(@RequestBody CategoriaAcademica categoria) {
        if (categoria.getActivo() == null) {
            categoria.setActivo(Boolean.TRUE);
        }
        CategoriaAcademica guardada = categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }
}

