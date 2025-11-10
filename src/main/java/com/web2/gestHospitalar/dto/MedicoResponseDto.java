package com.web2.gestHospitalar.dto;

import java.time.LocalDate;

public record MedicoResponseDto(
    Long id,
    String nome,
    String cpf,
    String email,
    String telefone,
    String endereco,
    LocalDate dataNascimento,
    LocalDate dataContratacao,
    Double salario,
    String cargo,
    String crm,
    String especialidade,
    String consultorio,
    String horarioAtendimento
) {}

