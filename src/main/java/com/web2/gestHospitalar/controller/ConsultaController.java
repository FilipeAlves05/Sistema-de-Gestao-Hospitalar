package com.web2.gestHospitalar.controller;

import com.web2.gestHospitalar.model.Consulta;
import com.web2.gestHospitalar.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return consultaService.get(id)
            .<ResponseEntity<?>>map(p -> ResponseEntity.ok(Map.of("message","Consulta encontrada","data",p)))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","Consulta não encontrada")));
    }

    @GetMapping
    public ResponseEntity<?> list(
        @RequestParam(required = false) String nome,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Consulta> result = consultaService.list(nome, page, size);
        return ResponseEntity.ok(Map.of("message","Lista de consultas","data", result.getContent(), "page", result.getNumber(), "size", result.getSize(), "totalElements", result.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Consulta consulta) {
        try {
            Consulta created = consultaService.create(consulta);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Consulta criada com sucesso","data",created));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao criar consulta"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Consulta consulta) {
        try {
            Consulta updated = consultaService.update(id, consulta);
            return ResponseEntity.ok(Map.of("message","Consulta atualizada com sucesso","data", updated));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao atualizar consulta"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            consultaService.delete(id);
            return ResponseEntity.ok(Map.of("message","Consulta removida com sucesso"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao remover consulta"));
        }
    }
}
