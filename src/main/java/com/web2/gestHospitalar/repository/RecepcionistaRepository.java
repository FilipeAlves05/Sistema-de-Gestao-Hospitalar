package com.web2.gestHospitalar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web2.gestHospitalar.model.Recepcionista;

public interface RecepcionistaRepository extends JpaRepository<Recepcionista, Long> {

    boolean existsByEmail(String email);

    Page<Recepcionista> findBySetor(String setor, Pageable pageable);

    boolean existsByCpf(String cpf);
}
