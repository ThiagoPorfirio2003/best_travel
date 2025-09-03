package com.curso_modulos_spring.best_travel.api.controllers;

import com.curso_modulos_spring.best_travel.api.models.requests.TicketRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TicketResponse;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.ITicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/ticket")
@Tag(name = "Ticket")
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

    @GetMapping(path = "/{uuid}")
    ResponseEntity<TicketResponse> get(@PathVariable(name = "uuid") UUID id)
    {
        return ResponseEntity.ok(this.ticketService.read(id));
    }

    @GetMapping
    ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam Long flyId)
    {
        return ResponseEntity.ok(Collections.singletonMap("flyPrice", this.ticketService.findPrice(flyId)));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TicketResponse> put(@PathVariable UUID id, @RequestBody TicketRequest request)
    {
        return ResponseEntity.ok(this.ticketService.update(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id)
    {
        this.ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
