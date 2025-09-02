package com.curso_modulos_spring.best_travel.infraesctructure.services;

import com.curso_modulos_spring.best_travel.api.models.requests.TourRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TourResponse;
import com.curso_modulos_spring.best_travel.domain.entities.*;
import com.curso_modulos_spring.best_travel.domain.repositories.CustomerRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.FlyRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.HotelRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.TourRepository;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.ITourService;
import com.curso_modulos_spring.best_travel.infraesctructure.helpers.CustomerHelper;
import com.curso_modulos_spring.best_travel.infraesctructure.helpers.TourHelper;
import com.curso_modulos_spring.best_travel.util.exceptions.IdNotFoundException;
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
    private final CustomerHelper customerHelper;

    @Autowired
    public TourService(TourRepository tourRepository,
                        FlyRepository flyRepository,
                        HotelRepository hotelRepository,
                        CustomerRepository customerRepository,
                       TourHelper tourHelper,
                       CustomerHelper customerHelper)
    {
        this.tourRepository = tourRepository;
        this.flyRepository = flyRepository;
        this.hotelRepository = hotelRepository;
        this.customerRepository = customerRepository;
        this.tourHelper = tourHelper;
        this.customerHelper = customerHelper;
    }

    @Override
    public void removeTicket(Long tourId, UUID ticketId)
    {
        var tourFromDB = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("tour"));

        tourFromDB.removeTicket(ticketId);

        this.tourRepository.save(tourFromDB);
    }

    @Override
    public UUID addTicket(Long tourId, Long flyId)
    {
        var tourFromDB = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("tour"));
        var fly = this.flyRepository.findById(flyId).orElseThrow(()-> new IdNotFoundException("fly"));
        var ticket = this.tourHelper.createTicket(fly, tourFromDB.getCustomer());

        tourFromDB.addTicket(ticket);

        this.tourRepository.save(tourFromDB);

        return ticket.getId();
    }

    @Override
    public void removeReservation(Long tourId, UUID reservationId)
    {
        var tourFromDB = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("tour"));

        tourFromDB.removeReservation(reservationId);

        this.tourRepository.save(tourFromDB);
    }

    @Override
    public UUID addReservation(Long tourId, Long hotelId, Integer totalDays)
    {
        var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("tour"));
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow(()-> new IdNotFoundException("hotel"));

        var reservation = this.tourHelper.createReservation(hotel, tourToUpdate.getCustomer(), totalDays);

        tourToUpdate.addReserervation(reservation);

        this.tourRepository.save(tourToUpdate);

        return reservation.getId();
    }

    @Override
    public TourResponse create(TourRequest request)
    {
        var customer = this.customerRepository.findById(request.getCustomerId()).orElseThrow(()-> new IdNotFoundException("customer"));
        var flights = new HashSet<FlyEntity>();
        var hotels = new HashMap<HotelEntity, Integer>();

        request.getFlights().forEach(tourFlyRequest->
        {
            flights.add(this.flyRepository.findById(tourFlyRequest.getId()).orElseThrow(()-> new IdNotFoundException("fly")));
        });

        request.getHotels().forEach(tourHotelRequest ->
        {
            hotels.put(this.hotelRepository.findById(tourHotelRequest.getId()).orElseThrow(()-> new IdNotFoundException("hotel")),
                    tourHotelRequest.getTotalDays());
        });

        var tourToPersist = TourEntity.builder()
                .customer(customer)
                .tickets(this.tourHelper.createTickets(flights, customer))
                .reservations(this.tourHelper.createReservations(hotels, customer))
                .build();


        var tourPersisted = this.tourRepository.save(tourToPersist);

        this.customerHelper.increase(customer.getDni(), TourService.class);

        return this.entityToResponse(tourPersisted);
    }

    @Override
    public TourResponse read(Long tourId)
    {
        var tour = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("tour"));

        return this.entityToResponse(tour);
    }

    @Override
    public void delete(Long tourId)
    {
        /*
            this.tourRepository.deleteById(tourId);
            La siguiente forma es como lo escribio el profe
         */

        var tourToDelete = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("tour"));
        this.tourRepository.delete(tourToDelete);
    }

    private TourResponse entityToResponse(TourEntity tourEntity)
    {
        return TourResponse.builder()
                .id(tourEntity.getId())
                .reservationIds(tourEntity.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourEntity.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .build();
    }
}
