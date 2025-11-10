package com.web2.gestHospitalar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web2.gestHospitalar.dto.MedicamentoDTO;
import com.web2.gestHospitalar.model.Medicamento;
import com.web2.gestHospitalar.repository.MedicamentoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MedicamentoService {
    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<MedicamentoDTO> listarMedicamentos(){
        return medicamentoRepository.findAllDTO();
    }

    public MedicamentoDTO buscarMedicamento(Long id) {
        return medicamentoRepository.findByIdDTO(id);
    }

    public Medicamento criarMedicamento(MedicamentoDTO medicamentoNovo) {
        Medicamento medicamento = new Medicamento();

        medicamento.setTipo(medicamentoNovo.tipo());
        medicamento.setNome(medicamentoNovo.nome());
        medicamento.setValidade(medicamentoNovo.validade());
        medicamento.setEstoque(null);
        medicamento.setPrescricao(null);

        Medicamento medicamentoSalvo = medicamentoRepository.save(medicamento);
        return medicamentoSalvo;
    }

    public Medicamento atualizarMedicamento(Long id, MedicamentoDTO medicamentoNovo) {
        Medicamento medicamentoExistente = medicamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento não encontrado com ID: " + id));


        medicamentoExistente.setTipo(medicamentoNovo.tipo());
        medicamentoExistente.setNome(medicamentoNovo.nome());
        medicamentoExistente.setValidade(medicamentoNovo.validade());
        medicamentoExistente.setEstoque(medicamentoNovo.estoque());
        medicamentoExistente.setPrescricao(medicamentoNovo.prescricao());

        Medicamento medicamentoAtualizado = medicamentoRepository.save(medicamentoExistente);
        return medicamentoAtualizado;
    }

    public void excluirMedicamento(Long id) {
        if (!medicamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Medicamento não encontrado com ID: " + id);
        }
        medicamentoRepository.deleteById(id);
    }
}
