package com.web2.gestHospitalar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web2.gestHospitalar.dto.EstoqueDTO;
import com.web2.gestHospitalar.model.Estoque;


@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    default List<EstoqueDTO> findAllDTO() {
        List<Estoque> estoques = findAll();
        return estoques.stream()
                .map(estoque -> new EstoqueDTO(
                        estoque.getId(),
                        estoque.getLocalizacao(),
                        estoque.getQuantidadeDisponivel(),
                        estoque.getMedicamento()
                ))
                .toList();
    }

    default EstoqueDTO findByIdDTO(Long id) {
        Estoque estoque = findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado com ID: " + id));
        return new EstoqueDTO(
                estoque.getId(),
                estoque.getLocalizacao(),
                estoque.getQuantidadeDisponivel(),
                estoque.getMedicamento()
        );
    }
}