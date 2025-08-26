package com.curso_modulos_spring.best_travel.infraesctructure.services;

import com.curso_modulos_spring.best_travel.api.models.requests.TicketRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TicketResponse;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.ITicketService;

import java.util.UUID;

public class TicketService implements ITicketService
{
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
}
