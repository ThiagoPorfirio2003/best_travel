package com.curso_modulos_spring.best_travel.api.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourFlyRequest
{
    @Positive(message = "The tour ID must be positive")
    @NotNull(message = "The tour ID is mandatory")
    private Long id;
}
