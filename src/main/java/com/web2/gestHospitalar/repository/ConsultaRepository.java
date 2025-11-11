package com.web2.gestHospitalar.repository;

import com.web2.gestHospitalar.model.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Page<Consulta> findByStatusContainingIgnoreCase(String status, Pageable pageable);
}
