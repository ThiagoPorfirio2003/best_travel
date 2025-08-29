package com.curso_modulos_spring.best_travel.infraesctructure.abstractservices;

import com.curso_modulos_spring.best_travel.api.models.responses.HotelResponse;

import java.util.Set;

public interface IHotelService extends ICatalogService<HotelResponse>
{
    Set<HotelResponse> readGreaterThan(Integer rating);
}
