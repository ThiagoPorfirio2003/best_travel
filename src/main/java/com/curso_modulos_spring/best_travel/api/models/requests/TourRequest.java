package com.curso_modulos_spring.best_travel.api.models.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 8, max = 20, message = "The length must be between 8 and 20 characters")
    @NotBlank(message = "The client ID is mandatory")
    private String customerId;

    @Valid
    @NotNull(message = "The flights are mandatory")
    @Size(min = 1)
    private Set<TourFlyRequest> flights;
    @Valid
    @NotNull(message = "The hotels are mandatory")
    @Size(min = 1)
    private Set<TourHotelRequest> hotels;
}
