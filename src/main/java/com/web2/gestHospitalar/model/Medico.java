package com.web2.gestHospitalar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
