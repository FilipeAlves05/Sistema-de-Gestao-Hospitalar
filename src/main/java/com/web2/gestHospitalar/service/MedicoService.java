package com.web2.gestHospitalar.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.gestHospitalar.dto.MedicoRequestDto;
import com.web2.gestHospitalar.dto.MedicoResponseDto;
import com.web2.gestHospitalar.mapper.MedicoMapper;
import com.web2.gestHospitalar.model.Medico;
import com.web2.gestHospitalar.repository.MedicoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public MedicoResponseDto criarMedico(MedicoRequestDto dto) {
        // Regra: não permitir CRM duplicado
        if (medicoRepository.existsByCrm(dto.crm())) {
            throw new IllegalArgumentException("CRM já cadastrado: " + dto.crm());
        }

        if (medicoRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email já cadastrado: " + dto.email());
        }

        if (medicoRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Cpf já cadastrado: " + dto.cpf());
        }



        Medico medico = MedicoMapper.toEntity(dto);
        medico.setDataContratacao(LocalDate.now());
        Medico salvo = medicoRepository.save(medico);
        return MedicoMapper.toResponseDto(salvo);
    }

    public Page<MedicoResponseDto> listarPorEspecialidade(String especialidade, Pageable pageable) {
        Page<Medico> medicos = medicoRepository.findByEspecialidade(especialidade, pageable);
        return medicos.map(MedicoMapper::toResponseDto);
    }

    public List<MedicoResponseDto> listarMedicos(){
        return medicoRepository.findAll()
                               .stream()
                               .map(MedicoMapper::toResponseDto)
                               .toList();
    }

    public MedicoResponseDto buscarPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado com ID: " + id));
        return MedicoMapper.toResponseDto(medico);
    }

    public MedicoResponseDto atualizarMedico(Long id, MedicoRequestDto dto) {
        Medico medicoExistente = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado com ID: " + id));

        // Regra: não permitir atualizar para um CRM já existente
        if (!medicoExistente.getCrm().equals(dto.crm()) && medicoRepository.existsByCrm(dto.crm())) {
            throw new IllegalArgumentException("CRM já cadastrado: " + dto.crm());
        }

        if (!medicoExistente.getEmail().equals(dto.email()) && medicoRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email já cadastrado: " + dto.email());
        }

        if (!medicoExistente.getCpf().equals(dto.cpf()) && medicoRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + dto.cpf());
        }


        medicoExistente.setNome(dto.nome());
        medicoExistente.setCpf(dto.cpf());
        medicoExistente.setEmail(dto.email());
        medicoExistente.setTelefone(dto.telefone());
        medicoExistente.setEndereco(dto.endereco());
        medicoExistente.setDataNascimento(dto.dataNascimento());
        medicoExistente.setCargo(dto.cargo());
        medicoExistente.setCrm(dto.crm());
        medicoExistente.setEspecialidade(dto.especialidade());
        medicoExistente.setConsultorio(dto.consultorio());
        medicoExistente.setHorarioAtendimento(dto.horarioAtendimento());

        Medico atualizado = medicoRepository.save(medicoExistente);
        return MedicoMapper.toResponseDto(atualizado);
    }

    // Excluir médico
    public void excluirMedico(Long id) {
        if (!medicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Médico não encontrado com ID: " + id);
        }
        medicoRepository.deleteById(id);
    }
}
