package com.web2.gestHospitalar.repository;

import com.web2.gestHospitalar.model.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Optional<Paciente> findByCpf(String cpf);
}
