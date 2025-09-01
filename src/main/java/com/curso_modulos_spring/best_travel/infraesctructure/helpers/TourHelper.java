package com.curso_modulos_spring.best_travel.infraesctructure.helpers;

import com.curso_modulos_spring.best_travel.domain.entities.*;
import com.curso_modulos_spring.best_travel.domain.repositories.ReservationRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.TicketRepository;
import com.curso_modulos_spring.best_travel.infraesctructure.services.ReservationService;
import com.curso_modulos_spring.best_travel.infraesctructure.services.TicketService;
import com.curso_modulos_spring.best_travel.util.BestTravelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Transactional
@Component
/*
    El proposito de este helper es evitar que TourService tenga como dependencias a
    TicketService y ReservationService, porque es una mala practica que un servicio
    dependa de otros.
    Entonces se crea este @Component con los metodos necesarios para que TourService
    pueda hacer las operaciones similares a las de TicketService y ReservationService
    sin tener que depender de esto
 */
//Se encarga de la creacion de tickets y reservaciones relacionadas con un tour
public class TourHelper
{
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public TourHelper(TicketRepository ticketRepository,
                      ReservationRepository reservationRepository)
    {
        this.ticketRepository = ticketRepository;
        this.reservationRepository = reservationRepository;
    }

    public Set<TicketEntity> createTickets(Set<FlyEntity> flights, CustomerEntity customer)
    {
        var response = new HashSet<TicketEntity>(flights.size());

        flights.forEach(fly -> {
            //Esta porcion es la misma de TicketService.create()
                var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().multiply(TicketService.CHAGER_PRICE_PERCENTAGE).add(fly.getPrice()))
                .purchaseDate(LocalDateTime.now())
                .departureDate(BestTravelUtil.getRandomSoon())
                .arrivalDate(BestTravelUtil.getRandomLatter())
                .build();

                response.add(this.ticketRepository.save(ticketToPersist));
        });

        return response;
    }

    //El integer de HashMap<HotelEntity, Integer> es la cantidad de dias, para
    public Set<ReservationEntity> createReservations(HashMap<HotelEntity, Integer> hotels, CustomerEntity customer)
    {
        var response = new HashSet<ReservationEntity>(hotels.size());

        hotels.forEach((hotel, totalDays) -> {
            //Esta porcion es la misma de TicketService.create()
            var reservationToPersist = ReservationEntity.builder()
                    .id(UUID.randomUUID())
                    .dateTimeReservation(LocalDateTime.now())
                    .dateStart(LocalDate.now())
                    .dateEnd(LocalDate.now().plusDays(totalDays))
                    .totalDays(totalDays)
                    .price(hotel.getPrice().multiply(ReservationService.CHARGES_PRICE_PERCENTAGE).add(hotel.getPrice()))
                    .hotel(hotel)
                    .customer(customer)
                    .build();

            response.add(this.reservationRepository.save(reservationToPersist));
        });

        return response;
    }
}
