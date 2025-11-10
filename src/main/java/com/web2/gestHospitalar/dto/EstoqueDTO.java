package com.web2.gestHospitalar.dto;

import com.web2.gestHospitalar.model.Medicamento;

public record EstoqueDTO(
    Long id,
    String localizacao,
    Integer quantidadeDisponivel,
    Medicamento medicamento
) {}

