package com.web2.gestHospitalar.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MedicoRequestDto(
    @NotBlank(message = "Nome é obrigatório")
    String nome,
    @NotBlank(message = "CPF é obrigatório")
    String cpf,
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email em um formato válido")
    String email,
    @NotBlank (message = "Telefone é obrigatório")
    String telefone,
    @NotBlank(message = "Endereço é obrigatório")
    String endereco,
    @NotNull(message = "Data de nascimento é obrigatória")
    LocalDate dataNascimento,
    @NotNull(message = "Valor não pode ser nulo")
    @Positive(message = "Valor tem que ser maior que zero")
    Double salario,
    @NotBlank(message = "Cargo não pode ser nulo")
    String cargo,
    @NotBlank(message = "CRM é obrigatório")
    String crm,
    @NotBlank(message = "Especialidade é obrigatória")
    String especialidade,
    @NotBlank(message = "Consultório é obrigatório")
    String consultorio,
    @NotBlank(message = "Hora do atendimento é obrigatório")
    String horarioAtendimento
) {
}
