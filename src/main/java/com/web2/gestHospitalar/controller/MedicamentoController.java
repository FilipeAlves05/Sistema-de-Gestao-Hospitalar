package com.web2.gestHospitalar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web2.gestHospitalar.model.Medicamento;
import com.web2.gestHospitalar.dto.MedicamentoDTO;
import com.web2.gestHospitalar.service.MedicamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }
    
    @GetMapping()
    public ResponseEntity<List<MedicamentoDTO>> listarMedicamentos(){
        return ResponseEntity.ok(medicamentoService.listarMedicamentos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> buscarMedicamento(@PathVariable Long id) {
        MedicamentoDTO medicamento = medicamentoService.buscarMedicamento(id);
        return ResponseEntity.ok(medicamento);
    }

    @PostMapping
    public ResponseEntity<Medicamento> criarMedicamento(@Valid @RequestBody MedicamentoDTO medicamentoDTO) {
        Medicamento criado = medicamentoService.criarMedicamento(medicamentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> atualizarMedicamento(
            @PathVariable Long id,
            @Valid @RequestBody MedicamentoDTO medicamentoDTO) {
        Medicamento atualizado = medicamentoService.atualizarMedicamento(id, medicamentoDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMedicamento(@PathVariable Long id) {
        medicamentoService.excluirMedicamento(id);
        return ResponseEntity.noContent().build();
    }
}
