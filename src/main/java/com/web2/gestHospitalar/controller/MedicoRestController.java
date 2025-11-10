package com.web2.gestHospitalar.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web2.gestHospitalar.dto.MedicoRequestDto;
import com.web2.gestHospitalar.dto.MedicoResponseDto;
import com.web2.gestHospitalar.service.MedicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicos")
public class MedicoRestController {

    private final MedicoService medicoService;

    public MedicoRestController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }
    
    @GetMapping()
    public ResponseEntity<List<MedicoResponseDto>> listarMedicos(){
        return ResponseEntity.ok(medicoService.listarMedicos());
    }

    @GetMapping("/especialidades")
    public ResponseEntity<Page<MedicoResponseDto>> listarPorEspecialidade(
            @RequestParam(required = false, defaultValue = "") String especialidade,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<MedicoResponseDto> medicos = medicoService.listarPorEspecialidade(especialidade, pageable);
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDto> buscarPorId(@PathVariable Long id) {
        MedicoResponseDto medico = medicoService.buscarPorId(id);
        return ResponseEntity.ok(medico);
    }

    @PostMapping
    public ResponseEntity<MedicoResponseDto> criarMedico(@Valid @RequestBody MedicoRequestDto medicoRequestDto) {
        MedicoResponseDto criado = medicoService.criarMedico(medicoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDto> atualizarMedico(
            @PathVariable Long id,
            @Valid @RequestBody MedicoRequestDto medicoRequestDto) {
        MedicoResponseDto atualizado = medicoService.atualizarMedico(id, medicoRequestDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMedico(@PathVariable Long id) {
        medicoService.excluirMedico(id);
        return ResponseEntity.noContent().build();
    }
}
