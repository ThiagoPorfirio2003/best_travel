package com.curso_modulos_spring.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder

@Entity(name = "customer")
public class CustomerEntity
{
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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<TourEntity> tours = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<TicketEntity> tickets = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ReservationEntity> reservations = new HashSet<>();

    public CustomerEntity()
    {
        this.tours = new HashSet<>();
        this.tickets = new HashSet<>();
        this.reservations = new HashSet<>();
    }

    public void addTicket(TicketEntity ticket)
    {
        this.tickets.add(ticket);
    }

    public void removeTicket(UUID id)
    {
        this.tickets.removeIf(ticket -> ticket.getId().toString().equals(id.toString()));
    }

    public void updateTickets()
    {
        this.tickets.forEach(ticket -> ticket.setCustomer(this));
    }

    public void addReserervation(ReservationEntity reservation)
    {
        this.reservations.add(reservation);
    }

    public void removeReservation(UUID id)
    {
        this.reservations.removeIf(reservation -> reservation.getId().toString().equals(id.toString()));
    }

    public void updateReservations()
    {
        this.reservations.forEach(reservation -> reservation.setCustomer(this));
    }

    public void addTour(TourEntity tourEntity)
    {
        this.tours.add(tourEntity);
    }

    public void removeTour(Long id)
    {
        this.tours.removeIf(tour -> tour.getId().toString().equals(id.toString()));
    }

    public void updateTour()
    {
        this.tours.forEach(reservation -> reservation.setCustomer(this));
    }
}
