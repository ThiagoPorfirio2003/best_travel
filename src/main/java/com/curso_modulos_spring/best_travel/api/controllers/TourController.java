package com.curso_modulos_spring.best_travel.api.controllers;

import com.curso_modulos_spring.best_travel.api.models.requests.TourRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TourResponse;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.ITourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<TourResponse> read(@RequestParam Long tourId)
    {
        return ResponseEntity.ok(this.tourService.read(tourId));
    }

    @DeleteMapping(path = "/{tourId}")
    public ResponseEntity<TourResponse> delete(@RequestParam Long tourId)
    {
        this.tourService.delete(tourId);

        return ResponseEntity.noContent().build();
    }
}
