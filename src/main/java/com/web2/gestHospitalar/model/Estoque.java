package com.web2.gestHospitalar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String localizacao;
    private Integer quantidadeDisponivel;

    @OneToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;
}
