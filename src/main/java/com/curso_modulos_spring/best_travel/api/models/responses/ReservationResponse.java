package com.curso_modulos_spring.best_travel.api.models.responses;

import com.curso_modulos_spring.best_travel.domain.entities.HotelEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse
{
    private UUID id;
    private LocalDateTime dateTimeReservation;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Integer totalDays;
    private BigDecimal price;
    private HotelEntity hotelEntity;
}
