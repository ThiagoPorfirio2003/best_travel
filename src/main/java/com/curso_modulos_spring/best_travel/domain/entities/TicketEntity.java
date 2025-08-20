package com.curso_modulos_spring.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name = "ticket")
public class TicketEntity
{
    @Id
    private UUID id;

    private BigDecimal price;

    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalDate purchaseDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fly_id")
    private FlyEntity fly;
}
