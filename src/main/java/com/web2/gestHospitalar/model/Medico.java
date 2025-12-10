package com.web2.gestHospitalar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.web2.gestHospitalar.security.Usuario;

@Entity
@Getter
@Setter
public class Medico extends Funcionario {

    @Column(nullable = false, unique = true)
    private String crm;
    private String especialidade;
    private String consultorio;
    private String horarioAtendimento;

    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas;

    @OneToMany(mappedBy = "medico")
    private List<Exame> exames;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
