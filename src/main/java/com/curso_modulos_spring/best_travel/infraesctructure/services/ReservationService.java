package com.curso_modulos_spring.best_travel.infraesctructure.services;

import com.curso_modulos_spring.best_travel.api.models.requests.ReservationRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.HotelResponse;
import com.curso_modulos_spring.best_travel.api.models.responses.ReservationResponse;
import com.curso_modulos_spring.best_travel.domain.entities.ReservationEntity;
import com.curso_modulos_spring.best_travel.domain.repositories.CustomerRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.HotelRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.ReservationRepository;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.IReservationService;
import com.curso_modulos_spring.best_travel.util.BestTravelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
@Slf4j
public class ReservationService implements IReservationService
{
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;

    private static final BigDecimal CHAGER_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);


    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                               CustomerRepository customerRepository,
                               HotelRepository hotelRepository)
    {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public ReservationResponse create(ReservationRequest request)
    {
        /*
            private String idClient;
            private Long idHotel;
       */
        var customer = this.customerRepository.findById(request.getIdClient()).orElseThrow();
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow();

        var reservationToSave = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(BestTravelUtil.getRandomSoon().toLocalDate())
                .dateEnd(BestTravelUtil.getRandomLatter().toLocalDate())
                .totalDays(BestTravelUtil.getRandomIntSoon())
                .price(hotel.getPrice().multiply(ReservationService.CHAGER_PRICE_PERCENTAGE).add(hotel.getPrice()))
                .hotel(hotel)
                .customer(customer)
                .build();

        var reservationSaved = this.reservationRepository.save(reservationToSave);

        log.info("Reservation saved with id {}", reservationSaved.getId());

        return this.entityToResponse(reservationSaved);
    }

    @Override
    public ReservationResponse read(UUID uuid) {
        return null;
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID uuid) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    private ReservationResponse entityToResponse(ReservationEntity reservation)
    {
        var response = new ReservationResponse();
        var hotelResponse = new HotelResponse();

        BeanUtils.copyProperties(reservation, response);
        BeanUtils.copyProperties(reservation.getHotel(), hotelResponse);

        response.setHotelResponse(hotelResponse);

        return response;
    }
}
