package com.curso_modulos_spring.best_travel.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponse
{
    private Long id;
    private String name;
    private String address;
    private Integer rating;
    private BigDecimal price;
}
