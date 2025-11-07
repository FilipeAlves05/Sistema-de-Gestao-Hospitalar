package com.web2.gestHospitalar.model.funcionario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Farmaceutico extends Funcionario{
    private String crf;
}
