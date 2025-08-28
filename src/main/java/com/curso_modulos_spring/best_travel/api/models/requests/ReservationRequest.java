package com.curso_modulos_spring.best_travel.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest
{
    private String idClient;
    private Long idHotel;
    private Integer totalDays;
}
