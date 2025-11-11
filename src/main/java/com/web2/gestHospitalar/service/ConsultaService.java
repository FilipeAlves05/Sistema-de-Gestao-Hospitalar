package com.web2.gestHospitalar.service;

import com.web2.gestHospitalar.model.Consulta;
import com.web2.gestHospitalar.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public Consulta create(Consulta consulta) {
        if (consulta.getPaciente() == null || consulta.getPaciente().getId() == null) {
            throw new IllegalArgumentException("Consulta deve referenciar um paciente existente.");
        }
        if (consulta.getMedico() == null || consulta.getMedico().getId() == null) {
            throw new IllegalArgumentException("Consulta deve referenciar um médico existente.");
        }
        return consultaRepository.save(consulta);
    }

    public Page<Consulta> list(String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataHora").descending());
        if (status != null && !status.isBlank()) {
            return consultaRepository.findByStatusContainingIgnoreCase(status, pageable);
        }
        return consultaRepository.findAll(pageable);
    }

    public Optional<Consulta> get(Long id) {
        return consultaRepository.findById(id);
    }

    public Consulta update(Long id, Consulta data) {
        Consulta c = consultaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
        c.setDataHora(data.getDataHora());
        c.setMotivo(data.getMotivo());
        c.setStatus(data.getStatus());
        c.setPaciente(data.getPaciente());
        c.setMedico(data.getMedico());
        return consultaRepository.save(c);
    }

    public void delete(Long id) {
        if (!consultaRepository.existsById(id)) {
            throw new IllegalArgumentException("Consulta não encontrada");
        }
        consultaRepository.deleteById(id);
    }
}
