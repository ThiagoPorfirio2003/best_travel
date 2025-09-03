package com.curso_modulos_spring.best_travel.api.controllers;

import com.curso_modulos_spring.best_travel.api.models.responses.FlyResponse;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.IFlyService;
import com.curso_modulos_spring.best_travel.util.enums.SortType;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(path = "/fly")
@Tag(name = "Fly")
public class FlyController
{
    private final IFlyService flyService;

    @Autowired
    public FlyController(IFlyService flyService)
    {
        this.flyService = flyService;
    }

    @GetMapping
    public ResponseEntity<Page<FlyResponse>> get(@RequestParam Integer pageNumber,
                                                 @RequestParam Integer pageSize,
                                                 @RequestHeader(required = false) SortType sortType)
    {
        if(Objects.isNull(sortType))
        {
            sortType = SortType.NONE;
        }

        var page = this.flyService.readAll(pageNumber, pageSize, sortType);

        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(page);
    }

    @GetMapping(path = "/less_price")
    public ResponseEntity<Set<FlyResponse>> getLessPrice(@RequestParam BigDecimal price)
    {
        return ResponseEntity.ok(this.flyService.readLessPrice(price));
    }

    @GetMapping(path = "/between_price")
    public ResponseEntity<Set<FlyResponse>> getBetweenPrice(@RequestParam BigDecimal min,
                                                            @RequestParam BigDecimal max)
    {
        return ResponseEntity.ok(this.flyService.readBetweenPrice(min, max));
    }

    @GetMapping(path = "/origin_destiny")
    public ResponseEntity<Set<FlyResponse>> getByOriginDestiny(@RequestParam String origin, @RequestParam String destiny)
    {
        return ResponseEntity.ok(this.flyService.readByOriginDestiny(origin, destiny));
    }
}