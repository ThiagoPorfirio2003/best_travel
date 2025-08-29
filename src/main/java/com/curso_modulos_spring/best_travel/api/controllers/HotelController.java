package com.curso_modulos_spring.best_travel.api.controllers;

import com.curso_modulos_spring.best_travel.api.models.responses.HotelResponse;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.IHotelService;
import com.curso_modulos_spring.best_travel.util.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(path = "/hotel")
public class HotelController
{
    private final IHotelService hotelService;

    @Autowired
    public HotelController(IHotelService hotelService)
    {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<Page<HotelResponse>> getAll(@RequestParam Integer pageNumber,
                                                       @RequestParam Integer pageSize,
                                                       @RequestHeader(required = false) SortType sortType)
    {
        if(Objects.isNull(sortType))
        {
            sortType = SortType.NONE;
        }

        return ResponseEntity.ok(this.hotelService.readAll(pageNumber, pageSize, sortType));
    }

    @GetMapping(path = "/less_price")
    public ResponseEntity<Set<HotelResponse>> getPrice(@RequestParam BigDecimal price)
    {
        return ResponseEntity.ok(this.hotelService.readLessPrice(price));
    }

    @GetMapping(path = "/between_price")
    public ResponseEntity<Set<HotelResponse>> getBetweenPrice(@RequestParam BigDecimal min,
                                                              @RequestParam BigDecimal max)
    {
        return ResponseEntity.ok(this.hotelService.readBetweenPrice(min, max));
    }

    @GetMapping(path = "/greater_than")
    public ResponseEntity<Set<HotelResponse>> getGreaterThan(@RequestParam Integer rating)
    {
        return ResponseEntity.ok(this.hotelService.readGreaterThan(rating));
    }
}
