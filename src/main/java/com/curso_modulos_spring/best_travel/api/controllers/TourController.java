package com.curso_modulos_spring.best_travel.api.controllers;

import com.curso_modulos_spring.best_travel.api.models.requests.TourRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TourResponse;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.ITourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/tour")
public class TourController
{
    private final ITourService tourService;

    @Autowired
    public TourController(ITourService tourService)
    {
        this.tourService = tourService;
    }

    @PostMapping
    public ResponseEntity<TourResponse> create(@RequestBody TourRequest request)
    {
        return ResponseEntity.ok(this.tourService.create(request));
    }

    @GetMapping(path = "/{tourId}")
    public ResponseEntity<TourResponse> read(@PathVariable Long tourId)
    {
        return ResponseEntity.ok(this.tourService.read(tourId));
    }

    @DeleteMapping(path = "/{tourId}")
    public ResponseEntity<Void> delete(@PathVariable Long tourId)
    {
        this.tourService.delete(tourId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String, UUID>> addTicket(@PathVariable Long tourId,
                                                       @PathVariable Long flyId)
    {
        var response = Collections.singletonMap("ticketId", this.tourService.addTicket(tourId, flyId));

        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> removeTicket(Long tourId, UUID ticketId)
    {
        this.tourService.removeTicket(tourId, ticketId);

        return ResponseEntity.noContent().build();
    }
}
