package com.web2.gestHospitalar.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Farmaceutico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String crf;

    private String estabelecimento;

    @OneToMany(mappedBy = "farmaceutico")
    private List<Prescricao> prescricoes;

    @OneToMany(mappedBy = "farmaceutico")
    private List<Medicamento> medicamentos;

    @OneToMany(mappedBy = "farmaceutico")
    private List<Estoque> estoques;
}
