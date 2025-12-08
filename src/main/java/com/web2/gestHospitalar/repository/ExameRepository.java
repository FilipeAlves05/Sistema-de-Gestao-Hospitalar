package com.web2.gestHospitalar.repository;

import com.web2.gestHospitalar.model.Exame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExameRepository extends JpaRepository<Exame, Long> {
    Page<Exame> findByStatusContainingIgnoreCase(String status, Pageable pageable);
}
