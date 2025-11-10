package com.web2.gestHospitalar.mapper;

import com.web2.gestHospitalar.dto.RecepcionistaRequestDto;
import com.web2.gestHospitalar.dto.RecepcionistaResponseDto;
import com.web2.gestHospitalar.model.Recepcionista;

public class RecepcionistaMapper {

    public static Recepcionista toEntity(RecepcionistaRequestDto dto) {
        if (dto == null) return null;

        Recepcionista r = new Recepcionista();
        r.setNome(dto.nome());
        r.setCpf(dto.cpf());
        r.setEmail(dto.email());
        r.setTelefone(dto.telefone());
        r.setEndereco(dto.endereco());
        r.setDataNascimento(dto.dataNascimento());
        r.setSalario(dto.salario());
        r.setCargo(dto.cargo());
        r.setSetor(dto.setor());
        r.setTurno(dto.turno());

        return r;
    }

    public static RecepcionistaResponseDto toResponseDto(Recepcionista r) {
        if (r == null) return null;

        return new RecepcionistaResponseDto(
                r.getId(),
                r.getNome(),
                r.getCpf(),
                r.getEmail(),
                r.getTelefone(),
                r.getEndereco(),
                r.getDataNascimento(),
                r.getDataContratacao(),
                r.getSalario(),
                r.getCargo(),
                r.getSetor(),
                r.getTurno()
        );
    }
}
