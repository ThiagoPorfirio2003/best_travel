package com.curso_modulos_spring.best_travel.domain.repositories;

import com.curso_modulos_spring.best_travel.domain.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Set;

public interface FlyRepository extends JpaRepository<FlyEntity, Long>
{
    @Query("select f from fly f where f.price < :price")
    //Buscamos los vuelos con menor precio al recibido
    Set<FlyEntity> selectLessPrice(BigDecimal price);

    @Query("select f from fly f where f.price between :minPrice and :maxPrice")
    Set<FlyEntity> selectBetweenPrice(BigDecimal minPrice, BigDecimal maxPrice);

    @Query("select f from fly f where f.originName = :origin and f.destinyName = :destiny")
    Set<FlyEntity> selectOriginDestiny(String origin, String destiny);
}
