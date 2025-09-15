package com.capassola.consultas.repository;


import com.capassola.consultas.model.Consulta;
import com.capassola.consultas.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByMedicoAndDiaAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Medico medico, LocalDate dia, LocalTime startTime, LocalTime enTime
    );
}
