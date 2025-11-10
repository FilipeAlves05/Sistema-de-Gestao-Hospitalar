package com.web2.gestHospitalar.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web2.gestHospitalar.dto.RecepcionistaRequestDto;
import com.web2.gestHospitalar.dto.RecepcionistaResponseDto;
import com.web2.gestHospitalar.service.RecepcionistaService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/recepcionistas")
public class RecepcionistaRestController {

    private final RecepcionistaService recepcionistaService;

    public RecepcionistaRestController(RecepcionistaService recepcionistaService) {
        this.recepcionistaService = recepcionistaService;
    }

    @GetMapping
    public ResponseEntity<List<RecepcionistaResponseDto>> listarRecepcionista() {
        return ResponseEntity.ok(recepcionistaService.listarRecepcionista());
    }
    

    @GetMapping("/setor")
    public ResponseEntity<Page<RecepcionistaResponseDto>> listarPorSetor(
            @RequestParam(required = false, defaultValue = "") String setor,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<RecepcionistaResponseDto> lista = recepcionistaService.listarPorSetor(setor, pageable);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecepcionistaResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(recepcionistaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RecepcionistaResponseDto> criar(@Valid @RequestBody RecepcionistaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recepcionistaService.criarRecepcionista(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecepcionistaResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RecepcionistaRequestDto dto) {
        return ResponseEntity.ok(recepcionistaService.atualizarRecepcionista(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        recepcionistaService.excluirRecepcionista(id);
        return ResponseEntity.noContent().build();
    }
}
