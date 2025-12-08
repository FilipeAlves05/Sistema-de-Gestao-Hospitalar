package com.web2.gestHospitalar.controller;

import com.web2.gestHospitalar.model.Exame;
import com.web2.gestHospitalar.service.ExameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/exames")
public class ExameController {

    @Autowired
    private ExameService exameService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return exameService.get(id)
            .<ResponseEntity<?>>map(p -> ResponseEntity.ok(Map.of("message","Exame encontrado","data",p)))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","Exame não encontrado")));
    }

    @GetMapping
    public ResponseEntity<?> list(
        @RequestParam(required = false) String nome,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Exame> result = exameService.list(nome, page, size);
        return ResponseEntity.ok(Map.of("message","Lista de exames","data", result.getContent(), "page", result.getNumber(), "size", result.getSize(), "totalElements", result.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Exame exame) {
        try {
            Exame created = exameService.create(exame);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Exame criado com sucesso","data",created));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao criar exame"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Exame exame) {
        try {
            Exame updated = exameService.update(id, exame);
            return ResponseEntity.ok(Map.of("message","Exame atualizado com sucesso","data", updated));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao atualizar exame"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            exameService.delete(id);
            return ResponseEntity.ok(Map.of("message","Exame removido com sucesso"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao remover exame"));
        }
    }
}
