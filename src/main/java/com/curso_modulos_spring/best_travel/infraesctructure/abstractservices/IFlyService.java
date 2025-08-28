package com.curso_modulos_spring.best_travel.infraesctructure.abstractservices;

import com.curso_modulos_spring.best_travel.api.models.responses.FlyResponse;

import java.util.Set;

public interface IFlyService extends ICatalogService<FlyResponse>
{
    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
}
