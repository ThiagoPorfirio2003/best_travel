package com.curso_modulos_spring.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name = "tour")
public class TourEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_customer")
    private CustomerEntity customer;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<TicketEntity> tickets;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ReservationEntity> reservations;

    public void addTicket(TicketEntity ticket)
    {
        if(Objects.isNull(this.tickets))
        {
            this.tickets = new HashSet<>();
        }
        this.tickets.add(ticket);
    }

    public void removeTicket(UUID id)
    {
        if(Objects.isNull(this.tickets))
        {
            this.tickets = new HashSet<>();
        }
        this.tickets.removeIf(ticket -> ticket.getId().equals(id));
    }

    /*
        Cuando agrego un ticket al tour, vinculo al tour con el ticket pero no al revez.
        Con este metodo le asigno este tour a cada ticket que fue asignado a este tour
    */
    public void updateTickets()
    {
        if(Objects.isNull(this.tickets))
        {
            this.tickets = new HashSet<>();
        }
        this.tickets.forEach(ticket -> ticket.setTour(this));
    }

    public void addReserervation(ReservationEntity reservation)
    {
        if(Objects.isNull(this.reservations))
        {
            this.reservations = new HashSet<>();
        }

        this.reservations.add(reservation);
    }

    public void removeReservation(UUID id)
    {
        if(Objects.isNull(this.reservations))
        {
            this.reservations = new HashSet<>();
        }
        this.reservations.removeIf(reservation -> reservation.equals(id));
    }

    public void updateReservations()
    {
        if(Objects.isNull(this.reservations))
        {
            this.reservations = new HashSet<>();
        }
        this.reservations.forEach(reservation -> reservation.setTour(this));
    }

    /*
        Un tour puede tickets y reservaciones, pero no
        necesariamente ambos
    */
}
