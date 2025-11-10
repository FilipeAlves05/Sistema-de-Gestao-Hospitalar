package com.web2.gestHospitalar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
public class Prescricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    @OneToMany(mappedBy = "prescricao")
    private List<Medicamento> medicamentos;
}
