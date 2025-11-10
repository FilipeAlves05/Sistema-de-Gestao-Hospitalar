package com.web2.gestHospitalar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String nome;
    private LocalDate validade;

    @OneToOne(mappedBy = "medicamento")
    private Estoque estoque;

    @ManyToOne
    @JoinColumn(name = "prescricao_id")
    private Prescricao prescricao;
}
