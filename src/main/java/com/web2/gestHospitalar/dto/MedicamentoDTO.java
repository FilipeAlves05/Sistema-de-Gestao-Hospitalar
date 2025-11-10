package com.web2.gestHospitalar.dto;

import java.time.LocalDate;

import com.web2.gestHospitalar.model.Estoque;
import com.web2.gestHospitalar.model.Prescricao;

public record MedicamentoDTO(
    Long id,
    String tipo,
    String nome,
    LocalDate validade,
    Estoque estoque,
    Prescricao prescricao
) {}

