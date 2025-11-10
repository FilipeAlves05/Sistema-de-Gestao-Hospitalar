package com.web2.gestHospitalar.service;

import com.web2.gestHospitalar.model.Prescricao;
import com.web2.gestHospitalar.repository.ConsultaRepository;
import com.web2.gestHospitalar.repository.PrescricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PrescricaoService {

    @Autowired
    private PrescricaoRepository prescricaoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public Prescricao create(Prescricao prescricao) {
        if (prescricao.getConsulta() == null || prescricao.getConsulta().getId() == null) {
            throw new IllegalArgumentException("Prescrição deve referenciar uma consulta existente.");
        }
        Long cid = prescricao.getConsulta().getId();
        if (!consultaRepository.existsById(cid)) {
            throw new IllegalArgumentException("Consulta referenciada não existe.");
        }
        return prescricaoRepository.save(prescricao);
    }

    public Page<Prescricao> list(Long consultaId, LocalDate start, LocalDate end, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("data").descending());
        if (consultaId != null) {
            return prescricaoRepository.findByConsulta_Id(consultaId, pageable);
        }
        if (start != null && end != null) {
            return prescricaoRepository.findByDataBetween(start, end, pageable);
        }
        return prescricaoRepository.findAll(pageable);
    }

    public Optional<Prescricao> get(Long id) {
        return prescricaoRepository.findById(id);
    }

    public Prescricao update(Long id, Prescricao data) {
        Prescricao p = prescricaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Prescrição não encontrada"));
        p.setData(data.getData());
        p.setObservacoes(data.getObservacoes());
        return prescricaoRepository.save(p);
    }

    public void delete(Long id) {
        if (!prescricaoRepository.existsById(id)) {
            throw new IllegalArgumentException("Prescrição não encontrada");
        }
        prescricaoRepository.deleteById(id);
    }
}
