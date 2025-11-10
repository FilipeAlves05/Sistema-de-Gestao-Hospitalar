package com.web2.gestHospitalar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web2.gestHospitalar.dto.EstoqueDTO;
import com.web2.gestHospitalar.model.Estoque;
import com.web2.gestHospitalar.repository.EstoqueRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;

    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public List<EstoqueDTO> listarEstoques(){
        return estoqueRepository.findAllDTO();
    }

    public EstoqueDTO buscarEstoque(Long id) {
        return estoqueRepository.findByIdDTO(id);
    }

    public Estoque criarEstoque(EstoqueDTO estoqueNovo) {
        Estoque estoque = new Estoque();

        estoque.setLocalizacao(estoqueNovo.localizacao());
        estoque.setQuantidadeDisponivel(estoqueNovo.quantidadeDisponivel());
        estoque.setMedicamento(estoqueNovo.medicamento());

        Estoque estoqueSalvo = estoqueRepository.save(estoque);
        return estoqueSalvo;
    }

    public Estoque atualizarEstoque(Long id, EstoqueDTO estoqueNovo) {
        Estoque estoqueExistente = estoqueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado com ID: " + id));


        estoqueExistente.setLocalizacao(estoqueNovo.localizacao());
        estoqueExistente.setQuantidadeDisponivel(estoqueNovo.quantidadeDisponivel());
        estoqueExistente.setMedicamento(estoqueNovo.medicamento());

        Estoque estoqueAtualizado = estoqueRepository.save(estoqueExistente);
        return estoqueAtualizado;
    }

    public void excluirEstoque(Long id) {
        if (!estoqueRepository.existsById(id)) {
            throw new EntityNotFoundException("Estoque não encontrado com ID: " + id);
        }
        estoqueRepository.deleteById(id);
    }
}
