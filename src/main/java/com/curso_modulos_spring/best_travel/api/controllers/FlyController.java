package com.curso_modulos_spring.best_travel.api.controllers;

import com.curso_modulos_spring.best_travel.api.models.responses.FlyResponse;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.IFlyService;
import com.curso_modulos_spring.best_travel.util.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/fly")
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
}
