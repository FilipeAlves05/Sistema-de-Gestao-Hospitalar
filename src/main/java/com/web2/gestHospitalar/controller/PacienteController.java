package com.web2.gestHospitalar.controller;

import com.web2.gestHospitalar.model.Paciente;
import com.web2.gestHospitalar.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Paciente paciente) {
        try {
            Paciente created = pacienteService.create(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Paciente criado com sucesso","data",created));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao criar paciente"));
        }
    }

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Paciente> result = pacienteService.list(nome, page, size);
        return ResponseEntity.ok(Map.of("message","Lista de pacientes","data", result.getContent(), "page", result.getNumber(), "size", result.getSize(), "totalElements", result.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return pacienteService.get(id)
                .<ResponseEntity<?>>map(p -> ResponseEntity.ok(Map.of("message","Paciente encontrado","data",p)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","Paciente não encontrado")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Paciente paciente) {
        try {
            Paciente updated = pacienteService.update(id, paciente);
            return ResponseEntity.ok(Map.of("message","Paciente atualizado com sucesso","data",updated));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao atualizar paciente"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            pacienteService.delete(id);
            return ResponseEntity.ok(Map.of("message","Paciente removido com sucesso"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao remover paciente"));
        }
    }
}
