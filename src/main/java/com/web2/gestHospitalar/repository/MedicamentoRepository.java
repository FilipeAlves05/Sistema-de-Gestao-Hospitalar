package com.web2.gestHospitalar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web2.gestHospitalar.dto.MedicamentoDTO;
import com.web2.gestHospitalar.model.Medicamento;


@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    default List<MedicamentoDTO> findAllDTO() {
        List<Medicamento> medicamentos = findAll();
        return medicamentos.stream()
                .map(medicamento -> new MedicamentoDTO(
                        medicamento.getId(),
                        medicamento.getTipo(),
                        medicamento.getNome(),
                        medicamento.getValidade(),
                        medicamento.getEstoque(),
                        medicamento.getPrescricao()
                ))
                .toList();
    }

    default MedicamentoDTO findByIdDTO(Long id) {
        Medicamento medicamento = findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado com ID: " + id));
        return new MedicamentoDTO(
                medicamento.getId(),
                medicamento.getTipo(),
                medicamento.getNome(),
                medicamento.getValidade(),
                medicamento.getEstoque(),
                medicamento.getPrescricao()
        );
    }
}