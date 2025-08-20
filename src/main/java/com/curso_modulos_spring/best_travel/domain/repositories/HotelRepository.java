package com.curso_modulos_spring.best_travel.domain.repositories;

import com.curso_modulos_spring.best_travel.domain.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
}
