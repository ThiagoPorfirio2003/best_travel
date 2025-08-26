package com.curso_modulos_spring.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private LocalDateTime purchaseDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fly_id")
    private FlyEntity fly;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id")
    private TourEntity tour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}
