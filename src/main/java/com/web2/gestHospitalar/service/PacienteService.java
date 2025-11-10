package com.web2.gestHospitalar.service;

import com.web2.gestHospitalar.model.Paciente;
import com.web2.gestHospitalar.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente create(Paciente paciente) {
        Optional<Paciente> existing = pacienteRepository.findByCpf(paciente.getCpf());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado para outro paciente.");
        }
        return pacienteRepository.save(paciente);
    }

    public Page<Paciente> list(String nomeFilter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        if (nomeFilter != null && !nomeFilter.isBlank()) {
            return pacienteRepository.findByNomeContainingIgnoreCase(nomeFilter, pageable);
        }
        return pacienteRepository.findAll(pageable);
    }

    public Optional<Paciente> get(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente update(Long id, Paciente data) {
        Paciente p = pacienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        p.setNome(data.getNome());
        p.setCpf(data.getCpf());
        p.setEmail(data.getEmail());
        p.setTelefone(data.getTelefone());
        p.setEndereco(data.getEndereco());
        p.setDataNascimento(data.getDataNascimento());
        p.setConvenio(data.getConvenio());
        p.setAlergias(data.getAlergias());
        return pacienteRepository.save(p);
    }

    public void delete(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }
        pacienteRepository.deleteById(id);
    }
}
