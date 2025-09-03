package com.curso_modulos_spring.best_travel.infraesctructure.services;

import com.curso_modulos_spring.best_travel.api.models.requests.TourRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TourResponse;
import com.curso_modulos_spring.best_travel.domain.entities.*;
import com.curso_modulos_spring.best_travel.domain.repositories.CustomerRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.FlyRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.HotelRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.TourRepository;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.ITourService;
import com.curso_modulos_spring.best_travel.infraesctructure.helpers.BlackListHelper;
import com.curso_modulos_spring.best_travel.infraesctructure.helpers.CustomerHelper;
import com.curso_modulos_spring.best_travel.infraesctructure.helpers.TourHelper;
import com.curso_modulos_spring.best_travel.util.enums.Tables;
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
    private final BlackListHelper blackListHelper;

    @Autowired
    public TourService(TourRepository tourRepository,
                        FlyRepository flyRepository,
                        HotelRepository hotelRepository,
                        CustomerRepository customerRepository,
                       TourHelper tourHelper,
                       CustomerHelper customerHelper,
                       BlackListHelper blackListHelper)
    {
        this.tourRepository = tourRepository;
        this.flyRepository = flyRepository;
        this.hotelRepository = hotelRepository;
        this.customerRepository = customerRepository;
        this.tourHelper = tourHelper;
        this.customerHelper = customerHelper;
        this.blackListHelper = blackListHelper;
    }

    @Override
    public void removeTicket(Long tourId, UUID ticketId)
    {
        var tourFromDB = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(Tables.tour.name()));

        tourFromDB.removeTicket(ticketId);

        this.tourRepository.save(tourFromDB);
    }

    @Override
    public UUID addTicket(Long tourId, Long flyId)
    {
        var tourFromDB = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(Tables.tour.name()));
        var fly = this.flyRepository.findById(flyId).orElseThrow(()-> new IdNotFoundException(Tables.fly.name()));
        var ticket = this.tourHelper.createTicket(fly, tourFromDB.getCustomer());

        tourFromDB.addTicket(ticket);

        this.tourRepository.save(tourFromDB);

        return ticket.getId();
    }

    @Override
    public void removeReservation(Long tourId, UUID reservationId)
    {
        var tourFromDB = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(Tables.tour.name()));

        tourFromDB.removeReservation(reservationId);

        this.tourRepository.save(tourFromDB);
    }

    @Override
    public UUID addReservation(Long tourId, Long hotelId, Integer totalDays)
    {
        var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(Tables.tour.name()));
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow(()-> new IdNotFoundException(Tables.hotel.name()));

        var reservation = this.tourHelper.createReservation(hotel, tourToUpdate.getCustomer(), totalDays);

        tourToUpdate.addReserervation(reservation);

        this.tourRepository.save(tourToUpdate);

        return reservation.getId();
    }

    @Override
    public TourResponse create(TourRequest request)
    {
        this.blackListHelper.isInBlackListCustomer(request.getCustomerId());
        var customer = this.customerRepository.findById(request.getCustomerId()).orElseThrow(()-> new IdNotFoundException(Tables.customer.name()));
        var flights = new HashSet<FlyEntity>();
        var hotels = new HashMap<HotelEntity, Integer>();

        request.getFlights().forEach(tourFlyRequest->
        {
            flights.add(this.flyRepository.findById(tourFlyRequest.getId()).orElseThrow(()-> new IdNotFoundException(Tables.fly.name())));
        });

        request.getHotels().forEach(tourHotelRequest ->
        {
            hotels.put(this.hotelRepository.findById(tourHotelRequest.getId()).orElseThrow(()-> new IdNotFoundException(Tables.hotel.name())),
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
        var tour = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(Tables.tour.name()));

        return this.entityToResponse(tour);
    }

    @Override
    public void delete(Long tourId)
    {
        /*
            this.tourRepository.deleteById(tourId);
            La siguiente forma es como lo escribio el profe
         */

        var tourToDelete = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(Tables.tour.name()));
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
