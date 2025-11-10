package com.web2.gestHospitalar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record RecepcionistaRequestDto(
    @NotBlank(message = "Nome é obrigatório")
    String nome,
    @NotBlank(message = "CPF é obrigatório")
    String cpf,
    @NotBlank(message = "Email é obrigatório")
    String email,
    @NotBlank(message = "Telefone é obrigatório")
    String telefone,
    @NotBlank(message = "Endereço é obrigatório")
    String endereco,
    @NotNull(message = "Data de nascimento é obrigatória")
    LocalDate dataNascimento,
    @NotNull(message = "Salário não pode ser nulo")
    @Positive(message = "Salario precisa ser positivo")
    Double salario,
    @NotBlank(message = "Cargo é obrigatóriro")
    String cargo,
    @NotBlank(message = "Setor é obrigatório")
    String setor,
    @NotBlank(message = "Turno é obrigatório")
    String turno
) {}
