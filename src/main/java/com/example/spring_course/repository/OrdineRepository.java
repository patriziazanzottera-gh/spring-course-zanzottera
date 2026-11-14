package com.example.spring_course.repository;

import com.example.spring_course.repository.model.OrdineCountProjection;
import com.example.spring_course.repository.model.OrdineEntity;
import com.example.spring_course.repository.model.Stato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrdineRepository extends JpaRepository<OrdineEntity, Long> {

    Optional<OrdineEntity> findByCodiceOrdine(String codiceOrdine);

    @Query(value = """
                SELECT COUNT(o.id) AS numeroOrdini,
                       o.stato AS stato
                FROM ordine o
                GROUP BY o.stato
                """, nativeQuery = true)
    List<OrdineCountProjection> findNumeroOrdiniByStato();

    List<OrdineEntity> findByStato(Stato stato);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ordine SET stato = :stato WHERE id = :id", nativeQuery = true)
    int patchStato(@Param("id")Long id, @Param("stato")String stato);
}

