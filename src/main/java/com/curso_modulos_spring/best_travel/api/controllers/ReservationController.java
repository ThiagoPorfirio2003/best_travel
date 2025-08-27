package com.curso_modulos_spring.best_travel.api.controllers;

import com.curso_modulos_spring.best_travel.api.models.requests.ReservationRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.ReservationResponse;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/reservation")
public class ReservationController
{
    private final IReservationService reservationService;

    @Autowired
    public ReservationController(IReservationService reservationService)
    {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest request)
    {
        return ResponseEntity.ok(this.reservationService.create(request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> read(@PathVariable UUID id)
    {
        return ResponseEntity.ok(this.reservationService.read(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> update(@PathVariable(name = "id") UUID idReservation, @RequestBody ReservationRequest request)
    {
        return ResponseEntity.ok(this.reservationService.update(request, idReservation));
    }

    @DeleteMapping(path = "/{reservationId}")
    public ResponseEntity<ReservationResponse> delete(@PathVariable UUID reservationId)
    {
        this.reservationService.delete(reservationId);

        return ResponseEntity.noContent().build();
    }
}
