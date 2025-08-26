package com.curso_modulos_spring.best_travel.api.controllers;

import com.curso_modulos_spring.best_travel.api.models.requests.TicketRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TicketResponse;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ticket")
public class TicketController
{
    private final ITicketService ticketService;

    @Autowired
    public TicketController(ITicketService ticketService)
    {
        this.ticketService = ticketService;
    }

    @PostMapping
    ResponseEntity<TicketResponse> post(@RequestBody TicketRequest request)
    {
        return ResponseEntity.ok(ticketService.create(request));
    }
}
