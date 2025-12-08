package com.web2.gestHospitalar.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.web2.gestHospitalar.model.Medico;
import com.web2.gestHospitalar.model.Paciente;
import com.web2.gestHospitalar.model.Prescricao;

public record ConsultaDTO(
    Long id,
    LocalDateTime dataHora,
    String motivo,
    String status,
    Paciente paciente,
    Medico medico,
    List<Prescricao> prescricoes
) {}
