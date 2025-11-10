package com.web2.gestHospitalar.controller;

import com.web2.gestHospitalar.model.Prescricao;
import com.web2.gestHospitalar.service.PrescricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/prescricoes")
public class PrescricaoController {

    @Autowired
    private PrescricaoService prescricaoService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Prescricao prescricao) {
        try {
            Prescricao created = prescricaoService.create(prescricao);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Prescrição criada com sucesso","data",created));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao criar prescrição"));
        }
    }

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(required = false) Long consultaId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Prescricao> result = prescricaoService.list(consultaId, start, end, page, size);
        return ResponseEntity.ok(Map.of("message","Lista de prescrições","data", result.getContent(), "page", result.getNumber(), "size", result.getSize(), "totalElements", result.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return prescricaoService.get(id)
                .<ResponseEntity<?>>map(p -> ResponseEntity.ok(Map.of("message","Prescrição encontrada","data",p)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","Prescrição não encontrada")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Prescricao prescricao) {
        try {
            Prescricao updated = prescricaoService.update(id, prescricao);
            return ResponseEntity.ok(Map.of("message","Prescrição atualizada com sucesso","data",updated));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao atualizar prescrição"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            prescricaoService.delete(id);
            return ResponseEntity.ok(Map.of("message","Prescrição removida com sucesso"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Erro ao remover prescrição"));
        }
    }
}
