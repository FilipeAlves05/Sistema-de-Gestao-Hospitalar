package com.web2.gestHospitalar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recepcionista extends Funcionario {

    private String setor;
    private String turno;
}
