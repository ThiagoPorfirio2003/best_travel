package com.curso_modulos_spring.best_travel.infraesctructure.services;

import com.curso_modulos_spring.best_travel.api.models.requests.ReservationRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.HotelResponse;
import com.curso_modulos_spring.best_travel.api.models.responses.ReservationResponse;
import com.curso_modulos_spring.best_travel.domain.entities.ReservationEntity;
import com.curso_modulos_spring.best_travel.domain.repositories.CustomerRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.FlyRepository;
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

    private static final BigDecimal CHARGES_PRICE_PERCENTAGE = BigDecimal.valueOf(0.20);


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
        var customer = this.customerRepository.findById(request.getIdClient()).orElseThrow();
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow();
        var dateStart = BestTravelUtil.getRandomSoon().toLocalDate();

        var reservationToSave = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(dateStart)
                .dateEnd(dateStart.plusDays(request.getTotalDays()))
                .totalDays(request.getTotalDays())
                .price(hotel.getPrice().multiply(ReservationService.CHARGES_PRICE_PERCENTAGE).add(hotel.getPrice()))
                .hotel(hotel)
                .customer(customer)
                .build();

        var reservationSaved = this.reservationRepository.save(reservationToSave);

        log.info("Reservation saved with id {}", reservationSaved.getId());

        return this.entityToResponse(reservationSaved);
    }

    @Override
    public ReservationResponse read(UUID uuid)
    {
        var reservationFromDB = this.reservationRepository.findById(uuid).orElseThrow();

        log.info("Reservation read with id {}", uuid);

        return this.entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID uuid)
    {
    /*
        private String idClient;
        private Long idHotel;
     */
        var reservationToUpdate = this.reservationRepository.findById(uuid).orElseThrow();
        var hotelFromDB = this.hotelRepository.findById(request.getIdHotel()).orElseThrow();
        var hotelPrice = hotelFromDB.getPrice();
        var dateStart = BestTravelUtil.getRandomSoon();

        reservationToUpdate.setHotel(hotelFromDB);
        reservationToUpdate.setPrice(hotelPrice.add(hotelPrice.multiply(ReservationService.CHARGES_PRICE_PERCENTAGE)));
        reservationToUpdate.setDateStart(dateStart.toLocalDate());
        reservationToUpdate.setDateEnd(dateStart.toLocalDate().plusDays(request.getTotalDays()));
        reservationToUpdate.setTotalDays(request.getTotalDays());


        this.reservationRepository.save(reservationToUpdate);

        log.info("Reservation updated with id {}", uuid);

        return this.entityToResponse(reservationToUpdate);
    }

    @Override
    public void delete(UUID uuid)
    {
        var reservationToDelete = this.reservationRepository.findById(uuid).orElseThrow();

        this.reservationRepository.delete(reservationToDelete);

        log.info("Reservation deleted with id: {}", uuid);
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

    @Override
    public BigDecimal findPrice(Long flyId) {
        var hotelFromDb = this.hotelRepository.findById(flyId).orElseThrow();

        return  hotelFromDb.getPrice().add(hotelFromDb.getPrice().multiply(ReservationService.CHARGES_PRICE_PERCENTAGE));
    }
}
