package com.web2.gestHospitalar.dto;

import java.time.LocalDate;

public record RecepcionistaResponseDto(
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
    String setor,
    String turno
) {}
