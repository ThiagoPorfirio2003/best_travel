package com.curso_modulos_spring.best_travel;

import com.curso_modulos_spring.best_travel.domain.repositories.FlyRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BestTravelApplication implements CommandLineRunner {

	@Autowired
	private FlyRepository flyRepository;

	@Autowired
	private HotelRepository hotelRepository;

	public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		 var fly = this.flyRepository.findById(15L).get();
		 var hotel = this.hotelRepository.findById(7L).get();

		 log.info(String.valueOf(fly));
		 log.info(String.valueOf(hotel));
	}
}
