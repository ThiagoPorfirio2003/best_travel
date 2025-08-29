package com.curso_modulos_spring.best_travel.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourRequest
{
    private String customerId;
    private Set<TourFlyRequest> flights;
    private Set<TourHotelRequest> hotels;
}
