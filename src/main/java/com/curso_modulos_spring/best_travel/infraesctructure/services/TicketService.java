package com.curso_modulos_spring.best_travel.infraesctructure.services;

import com.curso_modulos_spring.best_travel.api.models.requests.TicketRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.FlyResponse;
import com.curso_modulos_spring.best_travel.api.models.responses.TicketResponse;
import com.curso_modulos_spring.best_travel.domain.entities.TicketEntity;
import com.curso_modulos_spring.best_travel.domain.repositories.CustomerRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.FlyRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.TicketRepository;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.ITicketService;
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
public class TicketService implements ITicketService
{
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

    public static final BigDecimal CHAGER_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);

    @Autowired
    public TicketService(FlyRepository flyRepository,
                            CustomerRepository customerRepository,
                            TicketRepository ticketRepository)
    {
        this.flyRepository = flyRepository;
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketResponse create(TicketRequest request)
    {
        //Estos 3 metodos deberian ser una simple validacion para saber si la entidad existe
        var fly = this.flyRepository.findById(request.getIdFly()).orElseThrow();
        var customer = this.customerRepository.findById(request.getIdClient()).orElseThrow();

        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().multiply(TicketService.CHAGER_PRICE_PERCENTAGE).add(fly.getPrice()))
                .purchaseDate(LocalDateTime.now())
                .departureDate(BestTravelUtil.getRandomSoon())
                .arrivalDate(BestTravelUtil.getRandomLatter())
                .build();

        var ticketPersisted = this.ticketRepository.save(ticketToPersist);

        log.info("Ticket saved with id {}", ticketPersisted.getId());

        return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID uuid) {
        var ticketFromDB = this.ticketRepository.findById(uuid).orElseThrow();

        log.info("Ticket read with id {}", ticketFromDB.getId());

        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID uuid)
    {
        var ticketToUpdate = this.ticketRepository.findById(uuid).orElseThrow();
        /*
            En realidad esto deberia obtener el fly haciendo ticketToUpdate.getFly(), pero deberia validar
            que no sea null
       */
        var fly = this.flyRepository.findById(request.getIdFly()).orElseThrow();

        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().multiply(TicketService.CHAGER_PRICE_PERCENTAGE).add(fly.getPrice()));
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLatter());
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomSoon());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);

        log.info("Ticket updated with id: {}", ticketUpdated.getId());

        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID uuid)
    {
        var ticketToDelete = this.ticketRepository.findById(uuid).orElseThrow();

        this.ticketRepository.delete(ticketToDelete);

        log.info("Ticket deleted with id {}", uuid);
    }

    @Override
    public BigDecimal findPrice(Long idFly) {
        var fly = this.flyRepository.findById(idFly).orElseThrow();

        return fly.getPrice().multiply(TicketService.CHAGER_PRICE_PERCENTAGE).add(fly.getPrice());
    }

    private TicketResponse entityToResponse(TicketEntity ticketEntity)
    {
        var ticketResponse = new TicketResponse();
        var flyResponse = new FlyResponse();

        BeanUtils.copyProperties(ticketEntity, ticketResponse);
        BeanUtils.copyProperties(ticketEntity.getFly(), flyResponse);

        ticketResponse.setFly(flyResponse);

        return ticketResponse;
    }
}
