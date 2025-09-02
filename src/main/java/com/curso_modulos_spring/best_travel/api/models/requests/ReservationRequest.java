package com.curso_modulos_spring.best_travel.api.models.requests;

import jakarta.validation.constraints.*;
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
    @Size(min = 8, max = 20, message = "The length must be between 8 and 20 characters")
    @NotBlank(message = "The client ID is mandatory")
    private String idClient;
    @Positive
    @NotNull(message = "The hotel ID is mandatory")
    private Long idHotel;
    @Min(value = 1, message = "At least one day is required to make a reservation")
    @Max(value = 30, message = "The value cannot be greater than 30")
    @NotNull(message = "The totalDays is mandatory")
    private Integer totalDays;
    @Email
    private String email;
}
