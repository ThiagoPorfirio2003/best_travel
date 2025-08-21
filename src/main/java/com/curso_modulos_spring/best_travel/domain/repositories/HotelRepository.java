package com.curso_modulos_spring.best_travel.domain.repositories;

import com.curso_modulos_spring.best_travel.domain.entities.FlyEntity;
import com.curso_modulos_spring.best_travel.domain.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Set;

public interface HotelRepository extends JpaRepository<HotelEntity, Long>
{
    Set<HotelEntity> findByPriceLessThan(BigDecimal price);

    Set<HotelEntity> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    Set<HotelEntity> findByRatingGreaterThan(Integer rating);
}
