package com.web2.gestHospitalar.mapper;

import com.web2.gestHospitalar.dto.MedicoRequestDto;
import com.web2.gestHospitalar.dto.MedicoResponseDto;
import com.web2.gestHospitalar.model.Medico;

public class MedicoMapper {

    // Converte DTO de requisição para entidade
    public static Medico toEntity(MedicoRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Medico medico = new Medico();
        medico.setNome(dto.nome());
        medico.setCpf(dto.cpf());
        medico.setEmail(dto.email());
        medico.setTelefone(dto.telefone());
        medico.setEndereco(dto.endereco());
        medico.setDataNascimento(dto.dataNascimento());
        medico.setSalario(dto.salario());
        medico.setCargo(dto.cargo());
        medico.setCrm(dto.crm());
        medico.setEspecialidade(dto.especialidade());
        medico.setConsultorio(dto.consultorio());
        medico.setHorarioAtendimento(dto.horarioAtendimento());

        return medico;
    }

    // Converte entidade para DTO de resposta
    public static MedicoResponseDto toResponseDto(Medico medico) {
        if (medico == null)
            return null;

        MedicoResponseDto medicoResponseDto = new MedicoResponseDto(
                medico.getId(),
                medico.getNome(),
                medico.getCpf(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getEndereco(),
                medico.getDataNascimento(),
                medico.getDataContratacao(),
                medico.getSalario(),
                medico.getCargo(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getConsultorio(),
                medico.getHorarioAtendimento());

     return medicoResponseDto;
    }

}
