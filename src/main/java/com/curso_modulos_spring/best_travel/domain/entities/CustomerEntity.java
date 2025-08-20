package com.curso_modulos_spring.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name = "customer")
public class CustomerEntity
{
    /*
        dni            varchar(20) NOT NULL,
    full_name       varchar(50) NOT NULL,
    credit_card    varchar(20) NOT NULL,
    total_flights  int NOT NULL,
    total_lodgings int NOT NULL,
    total_tours    int NOT NULL,
    phone_number  varchar(20) NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY ( dni )
     */

    @Id
    @Column(length = 20)
    private String dni;

    @Column(length = 50)
    private String fullName;

    @Column(length = 20)
    private String creditCard;

    private Integer totalFlights;
    private Integer totalLodgings;
    private Integer totalTours;

    @Column(length = 20)
    private String phoneNumber;

    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<TourEntity> tours;

    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<TicketEntity> tickets;

    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ReservationEntity> reservations;
}
