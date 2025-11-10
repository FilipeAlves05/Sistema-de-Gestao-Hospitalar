package com.web2.gestHospitalar.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.gestHospitalar.dto.RecepcionistaRequestDto;
import com.web2.gestHospitalar.dto.RecepcionistaResponseDto;
import com.web2.gestHospitalar.mapper.RecepcionistaMapper;
import com.web2.gestHospitalar.model.Recepcionista;
import com.web2.gestHospitalar.repository.RecepcionistaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RecepcionistaService {

    private final RecepcionistaRepository recepcionistaRepository;

    public RecepcionistaService(RecepcionistaRepository recepcionistaRepository) {
        this.recepcionistaRepository = recepcionistaRepository;
    }

    public RecepcionistaResponseDto criarRecepcionista(RecepcionistaRequestDto dto) {
        if (recepcionistaRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + dto.cpf());
        }

        if (recepcionistaRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email já cadastrado: " + dto.email());
        }

        Recepcionista r = RecepcionistaMapper.toEntity(dto);
        r.setDataContratacao(LocalDate.now());
        Recepcionista salvo = recepcionistaRepository.save(r);
        return RecepcionistaMapper.toResponseDto(salvo);
    }

    public List<RecepcionistaResponseDto> listarRecepcionista(){
        return recepcionistaRepository.findAll()
                                      .stream()
                                      .map(RecepcionistaMapper::toResponseDto)
                                      .toList();
    }

    public Page<RecepcionistaResponseDto> listarPorSetor(String setor, Pageable pageable) {
        Page<Recepcionista> page = recepcionistaRepository.findBySetor(setor, pageable);
        return page.map(RecepcionistaMapper::toResponseDto);
    }

    public RecepcionistaResponseDto buscarPorId(Long id) {
        Recepcionista r = recepcionistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recepcionista não encontrado com ID: " + id));
        return RecepcionistaMapper.toResponseDto(r);
    }

    public RecepcionistaResponseDto atualizarRecepcionista(Long id, RecepcionistaRequestDto dto) {
        Recepcionista r = recepcionistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recepcionista não encontrado com ID: " + id));

        if (!r.getCpf().equals(dto.cpf()) && recepcionistaRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + dto.cpf());
        }

        if (!r.getEmail().equals(dto.email()) && recepcionistaRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email já cadastrado: " + dto.email());
        }

        r.setNome(dto.nome());
        r.setCpf(dto.cpf());
        r.setEmail(dto.email());
        r.setTelefone(dto.telefone());
        r.setEndereco(dto.endereco());
        r.setDataNascimento(dto.dataNascimento());
        r.setSetor(dto.setor());
        r.setTurno(dto.turno());

        Recepcionista atualizado = recepcionistaRepository.save(r);
        return RecepcionistaMapper.toResponseDto(atualizado);
    }

    public void excluirRecepcionista(Long id) {
        if (!recepcionistaRepository.existsById(id)) {
            throw new EntityNotFoundException("Recepcionista não encontrado com ID: " + id);
        }
        recepcionistaRepository.deleteById(id);
    }
}
