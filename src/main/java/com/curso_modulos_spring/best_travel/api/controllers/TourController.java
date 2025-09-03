package com.curso_modulos_spring.best_travel.api.controllers;

import com.curso_modulos_spring.best_travel.api.models.requests.TourRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TourResponse;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.ITourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/tour")
@Tag(name = "Tour")
public class TourController
{
    private final ITourService tourService;

    @Autowired
    public TourController(ITourService tourService)
    {
        this.tourService = tourService;
    }

    @Operation(summary = "Guarda un tour en la DB")
    @PostMapping
    public ResponseEntity<TourResponse> create(@Valid @RequestBody TourRequest request)
    {
        return ResponseEntity.ok(this.tourService.create(request));
    }

    @Operation(summary = "Retorna un tour con el ID recibido")
    @GetMapping(path = "/{tourId}")
    public ResponseEntity<TourResponse> read(@PathVariable Long tourId)
    {
        return ResponseEntity.ok(this.tourService.read(tourId));
    }

    @Operation(summary = "Elimina el tour que coincide con el ID recibido")
    @DeleteMapping(path = "/{tourId}")
    public ResponseEntity<Void> delete(@PathVariable Long tourId)
    {
        this.tourService.delete(tourId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Agrega un ticket a un tour")
    @PatchMapping(path = "/{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String, UUID>> addTicket(@PathVariable Long tourId,
                                                       @PathVariable Long flyId)
    {
        var response = Collections.singletonMap("ticketId", this.tourService.addTicket(tourId, flyId));

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Elimina un ticket de un tour")
    @PatchMapping(path = "/{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> removeTicket(@PathVariable Long tourId,
                                             @PathVariable  UUID ticketId)
    {
        this.tourService.removeTicket(tourId, ticketId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Agrega una reservation a un tour")
    @PatchMapping(path = "/{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String, UUID>> addReservation(@PathVariable Long tourId,
                                                       @PathVariable Long hotelId,
                                                       @RequestParam Integer totalDays)
    {
        var response = Collections.singletonMap("reservationId", this.tourService.addReservation(tourId, hotelId,totalDays));

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Elimina una reservation a un tour")
    @PatchMapping(path = "/{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> removeReservation(@PathVariable Long tourId,
                                                  @PathVariable UUID reservationId)
    {
        this.tourService.removeReservation(tourId, reservationId);

        return ResponseEntity.noContent().build();
    }
}
