package com.web2.gestHospitalar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web2.gestHospitalar.model.Estoque;
import com.web2.gestHospitalar.dto.EstoqueDTO;
import com.web2.gestHospitalar.service.EstoqueService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }
    
    @GetMapping()
    public ResponseEntity<List<EstoqueDTO>> listarEstoques(){
        return ResponseEntity.ok(estoqueService.listarEstoques());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueDTO> buscarEstoque(@PathVariable Long id) {
        EstoqueDTO estoque = estoqueService.buscarEstoque(id);
        return ResponseEntity.ok(estoque);
    }

    @PostMapping
    public ResponseEntity<Estoque> criarEstoque(@Valid @RequestBody EstoqueDTO estoqueDTO) {
        Estoque criado = estoqueService.criarEstoque(estoqueDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estoque> atualizarEstoque(
            @PathVariable Long id,
            @Valid @RequestBody EstoqueDTO estoqueDTO) {
        Estoque atualizado = estoqueService.atualizarEstoque(id, estoqueDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEstoque(@PathVariable Long id) {
        estoqueService.excluirEstoque(id);
        return ResponseEntity.noContent().build();
    }
}
