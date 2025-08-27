package com.curso_modulos_spring.best_travel.api.controllers;

import com.curso_modulos_spring.best_travel.api.models.requests.ReservationRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.ReservationResponse;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
