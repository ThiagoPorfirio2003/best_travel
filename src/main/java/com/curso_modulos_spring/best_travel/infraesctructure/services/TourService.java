package com.curso_modulos_spring.best_travel.infraesctructure.services;

import com.curso_modulos_spring.best_travel.api.models.requests.TourRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TourResponse;
import com.curso_modulos_spring.best_travel.domain.entities.*;
import com.curso_modulos_spring.best_travel.domain.repositories.CustomerRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.FlyRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.HotelRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.TourRepository;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.ITourService;
import com.curso_modulos_spring.best_travel.infraesctructure.helpers.TourHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class TourService implements ITourService
{
    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;

    @Autowired
    public TourService(TourRepository tourRepository,
                        FlyRepository flyRepository,
                        HotelRepository hotelRepository,
                        CustomerRepository customerRepository,
                       TourHelper tourHelper)
    {
        this.tourRepository = tourRepository;
        this.flyRepository = flyRepository;
        this.hotelRepository = hotelRepository;
        this.customerRepository = customerRepository;
        this.tourHelper = tourHelper;
    }

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {

    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        return null;
    }

    @Override
    public void removeReservation(UUID reservationId, Long tourId) {

    }

    @Override
    public UUID addReservation(UUID reservationId, Long tourId) {
        return null;
    }

    @Override
    public TourResponse create(TourRequest request)
    {
        var customer = this.customerRepository.findById(request.getCustomerId()).orElseThrow();
        var flights = new HashSet<FlyEntity>();
        var hotels = new HashMap<HotelEntity, Integer>();

        request.getFlights().forEach(tourFlyRequest->
        {
            flights.add(this.flyRepository.findById(tourFlyRequest.getId()).orElseThrow());
        });

        request.getHotels().forEach(tourHotelRequest ->
        {
            hotels.put(this.hotelRepository.findById(tourHotelRequest.getId()).orElseThrow(),
                    tourHotelRequest.getTotalDays());
        });

        var tourToPersist = TourEntity.builder()
                .customer(customer)
                .tickets(this.tourHelper.createTickets(flights, customer))
                .reservations(this.tourHelper.createReservations(hotels, customer))
                .build();

        var tourPersisted = this.tourRepository.save(tourToPersist);

        return this.entityToResponse(tourPersisted);
    }

    @Override
    public TourResponse read(Long tourId)
    {
        var tour = this.tourRepository.findById(tourId).orElseThrow();

        //tour.getTickets().m

        return null;
    }

    @Override
    public void delete(Long tourId) {

    }

    private TourResponse entityToResponse(TourEntity tourEntity)
    {
        /*
            private Long id;
    private Set<UUID> ticketIds;
    private Set<UUID> reservationIds;
         */

        return TourResponse.builder()
                .id(tourEntity.getId())
                .reservationIds(tourEntity.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourEntity.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .build();
    }
}
