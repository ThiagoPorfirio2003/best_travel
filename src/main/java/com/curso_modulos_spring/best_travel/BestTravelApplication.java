package com.curso_modulos_spring.best_travel;

import com.curso_modulos_spring.best_travel.domain.entities.ReservationEntity;
import com.curso_modulos_spring.best_travel.domain.entities.TicketEntity;
import com.curso_modulos_spring.best_travel.domain.entities.TourEntity;
import com.curso_modulos_spring.best_travel.domain.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class BestTravelApplication implements CommandLineRunner {

	private final FlyRepository flyRepository;
	private final HotelRepository hotelRepository;
	private final CustomerRepository customerRepository;
	private final ReservationRepository reservationRepository;
	private final TicketRepository ticketRepository;
	private final TourRepository tourRepository;

	//@Autowired
	public BestTravelApplication(FlyRepository flyRepository,
								 HotelRepository hotelRepository,
								 CustomerRepository customerRepository,
								 ReservationRepository reservationRepository,
								 TicketRepository ticketRepository,
								 TourRepository tourRepository)
	{
		this.flyRepository = flyRepository;
		this.hotelRepository = hotelRepository;
		this.customerRepository = customerRepository;
		this.reservationRepository = reservationRepository;
		this.ticketRepository = ticketRepository;
		this.tourRepository = tourRepository;

	}

	public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		/*
		var fly = this.flyRepository.findById(15L).get();
		var hotel = this.hotelRepository.findById(7L).get();
		var ticket = this.ticketRepository.findById(UUID.fromString("32345678-1234-5678-4234-567812345678")).get();
		var reservation = this.reservationRepository.findById(UUID.fromString("32345678-1234-5678-1234-567812345678")).get();
		var customer = this.customerRepository.findById("BBMB771012HMCRR022").get();
		*/

		/*
		log.info(String.valueOf(fly));
		log.info(String.valueOf(hotel));
		log.info(String.valueOf(ticket));
		log.info(String.valueOf(reservation));
		log.info(String.valueOf(customer));
		*/

		//this.flyRepository.selectLessPrice(BigDecimal.valueOf(20)).forEach(System.out::println);
		//this.flyRepository.selectBetweenPrice(BigDecimal.valueOf(10), BigDecimal.valueOf(15)).forEach(System.out::println);
		//this.flyRepository.selectOriginDestiny("Grecia", "Mexico").forEach(System.out::println);
		//System.out.println(this.flyRepository.findByTicketId(UUID.fromString("12345678-1234-5678-2236-567812345678")).get());

		//this.hotelRepository.findByPriceLessThan(BigDecimal.valueOf(100)).forEach(System.out::println);
		//this.hotelRepository.findByPriceBetween(BigDecimal.valueOf(100), BigDecimal.valueOf(150)).forEach(System.out::println);

		//this.hotelRepository.findByRatingGreaterThan(3).forEach(System.out::println);

		//System.out.println(this.hotelRepository.findByReservationsId(UUID.fromString("12345678-1234-5678-1234-567812345678")));



		var customer = this.customerRepository.findById("GOTW771012HMRGR087").get();
		var hotel = this.hotelRepository.findById(3L).get();
		var fly = flyRepository.findById(11L).get();

		var tour = TourEntity.builder()
				.customer(customer)
				.build();

		var ticket = TicketEntity.builder()
				.id(UUID.randomUUID())
				.price(fly.getPrice().multiply(BigDecimal.TEN))
				.arrivalDate(LocalDate.now())
				.departureDate(LocalDate.now())
				.purchaseDate(LocalDate.now())
				.customer(customer)
				.tour(tour)
				.fly(fly)
				.build();

		var reservation = ReservationEntity.builder()
				.id(UUID.randomUUID())
				.customer(customer)
				.dateTimeReservation(LocalDateTime.now())
				.dateEnd(LocalDate.now().plusDays(2))
				.dateStart(LocalDate.now().plusDays(1))
				.hotel(hotel)
				.tour(tour)
				.totalDays(1)
				.price(hotel.getPrice().multiply(BigDecimal.TEN))
				.build();

		tour.addReserervation(reservation);
		tour.addTicket(ticket);
		tour.updateReservations();
		tour.updateTickets();

		this.tourRepository.save(tour);
	}
}
