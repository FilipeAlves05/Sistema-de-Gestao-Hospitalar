package com.web2.gestHospitalar.service;

import com.web2.gestHospitalar.model.Exame;
import com.web2.gestHospitalar.repository.ExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExameService {

    @Autowired
    private ExameRepository exameRepository;

    public Optional<Exame> get(Long id) {
        return exameRepository.findById(id);
    }

    public Page<Exame> list(String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataRealizacao").descending());
        if (status != null && !status.isBlank()) {
            return exameRepository.findByStatusContainingIgnoreCase(status, pageable);
        }
        return exameRepository.findAll(pageable);
    }

    public Exame create(Exame exame) {
        if (exame.getPaciente() == null || exame.getPaciente().getId() == null) {
            throw new IllegalArgumentException("Exame deve referenciar um paciente existente.");
        }
        if (exame.getMedico() == null || exame.getMedico().getId() == null) {
            throw new IllegalArgumentException("Exame deve referenciar um médico existente.");
        }
        return exameRepository.save(exame);
    }

    public Exame update(Long id, Exame data) {
        Exame exame = exameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exame não encontrado"));
        exame.setResultado(data.getResultado());
        exame.setLaudo(data.getLaudo());
        exame.setObservacoes(data.getLaudo());
        exame.setNomeExame(data.getLaudo());
        exame.setDataSolicitacao(data.getDataSolicitacao());
        exame.setDataRealizacao(data.getDataRealizacao());
        exame.setStatus(data.getStatus());
        exame.setPaciente(data.getPaciente());
        exame.setMedico(data.getMedico());
        return exameRepository.save(exame);
    }

    public void delete(Long id) {
        if (!exameRepository.existsById(id)) {
            throw new IllegalArgumentException("Exame não encontrado");
        }
        exameRepository.deleteById(id);
    }
}
