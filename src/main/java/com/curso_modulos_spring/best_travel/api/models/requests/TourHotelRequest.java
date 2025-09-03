package com.curso_modulos_spring.best_travel.api.models.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourHotelRequest
{
    @Positive(message = "The hotel ID must be positive")
    @NotNull(message = "The hotel ID is mandatory")
    private Long id;
    @Positive
    @Min(value = 1, message = "At least one day is required to make a reservation")
    @Max(value = 30, message = "The value cannot be greater than 30")
    @NotNull(message = "The totalDays is mandatory")
    private Integer totalDays;
}
