package com.web2.gestHospitalar.model;

import com.web2.gestHospitalar.security.Usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recepcionista extends Funcionario {
    private String setor;
    private String turno;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
