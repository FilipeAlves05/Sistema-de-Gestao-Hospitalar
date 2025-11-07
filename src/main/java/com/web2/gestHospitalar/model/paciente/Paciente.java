package com.web2.gestHospitalar.model.paciente;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import com.web2.gestHospitalar.model.consulta.Consulta;

@Entity
@Getter
@Setter
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;
    private LocalDate dataNascimento;

    private String convenio;
    private String alergias;

    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas;
}
