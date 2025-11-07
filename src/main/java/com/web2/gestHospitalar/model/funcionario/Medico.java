package com.web2.gestHospitalar.model.funcionario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.web2.gestHospitalar.model.consulta.Consulta;

@Entity
@Getter
@Setter
public class Medico extends Funcionario {

    private String crm;
    private String especialidade;
    private String consultorio;
    private String horarioAtendimento;

    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas;
}
