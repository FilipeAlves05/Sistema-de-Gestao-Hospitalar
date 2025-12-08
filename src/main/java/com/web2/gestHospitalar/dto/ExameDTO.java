package com.web2.gestHospitalar.dto;

import java.time.LocalDateTime;

import com.web2.gestHospitalar.model.Medico;
import com.web2.gestHospitalar.model.Paciente;

public record ExameDTO(
    Long id,
    String resultado,
    String laudo,
    String observacoes,
    String nomeExame,
    LocalDateTime dataSolicitacao,
    LocalDateTime dataRealizacao,
    String status,
    Medico medico,
    Paciente paciente
) {}
