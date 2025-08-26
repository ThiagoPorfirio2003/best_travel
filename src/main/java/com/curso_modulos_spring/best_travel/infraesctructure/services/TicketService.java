package com.curso_modulos_spring.best_travel.infraesctructure.services;

import com.curso_modulos_spring.best_travel.api.models.requests.TicketRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.FlyResponse;
import com.curso_modulos_spring.best_travel.api.models.responses.TicketResponse;
import com.curso_modulos_spring.best_travel.domain.entities.TicketEntity;
import com.curso_modulos_spring.best_travel.domain.repositories.CustomerRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.FlyRepository;
import com.curso_modulos_spring.best_travel.domain.repositories.TicketRepository;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
@Slf4j
public class TicketService implements ITicketService
{
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

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
    public TicketResponse create(TicketRequest request) {
        return null;
    }

    @Override
    public TicketResponse read(UUID uuid) {
        return null;
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID uuid) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

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
