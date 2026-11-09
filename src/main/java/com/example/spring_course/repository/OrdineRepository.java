package com.example.spring_course.repository;

import com.example.spring_course.repository.model.OrdineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrdineRepository extends JpaRepository<OrdineEntity, Long> {

    Optional<OrdineEntity> findByCodiceOrdine(String codiceOrdine);

    @Query(value = "SELECT count(id),stato FROM Ordine WHERE stato = :stato GROUP BY stato", nativeQuery = true)
    OrdineCountProjection findNumeroOrdiniByStato(@Param("stato")String stato);
}

