package com.web2.gestHospitalar.repository;


import com.web2.gestHospitalar.model.Prescricao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrescricaoRepository extends JpaRepository<Prescricao, Long> {
    Page<Prescricao> findByConsulta_Id(Long consultaId, Pageable pageable);
    Page<Prescricao> findByDataBetween(LocalDate start, LocalDate end, Pageable pageable);
}
