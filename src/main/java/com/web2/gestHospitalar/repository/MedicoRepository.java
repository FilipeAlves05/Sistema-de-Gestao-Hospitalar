package com.web2.gestHospitalar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web2.gestHospitalar.model.Medico;


@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    boolean existsByEmail(String email);

    boolean existsByCrm(String crm);

    boolean existsByCpf(String cpf);

    Page<Medico> findByEspecialidade(String especialidade, Pageable pageable);
}