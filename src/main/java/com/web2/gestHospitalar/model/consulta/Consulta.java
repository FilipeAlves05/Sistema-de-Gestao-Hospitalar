package com.web2.gestHospitalar.model.consulta;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import com.web2.gestHospitalar.model.funcionario.Medico;
import com.web2.gestHospitalar.model.paciente.Paciente;
import com.web2.gestHospitalar.model.prescricao.Prescricao;

@Entity
@Getter
@Setter
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;
    private String motivo;
    private String status;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @OneToMany(mappedBy = "consulta")
    private List<Prescricao> prescricoes;
    
}
