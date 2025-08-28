package com.curso_modulos_spring.best_travel.infraesctructure.services;

import com.curso_modulos_spring.best_travel.api.models.responses.FlyResponse;
import com.curso_modulos_spring.best_travel.domain.repositories.FlyRepository;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.IFlyService;
import com.curso_modulos_spring.best_travel.util.SortType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
@Slf4j
public class FlyService implements IFlyService
{
    private final FlyRepository flyRepository;


    @Autowired
    public FlyService(FlyRepository flyRepository)
    {
        this.flyRepository = flyRepository;
    }

    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny)
    {
        /*
        var fliesFromDB = this.flyRepository.selectOriginDestiny(origin, destiny);
        var fliesResponses = new HashSet<FlyResponse>();

        return flies;
        */
        return null;
    }

    @Override
    public Page<FlyResponse> readAll(Integer pageNumber, Integer pageSize, SortType sortType) {
        return null;
    }

    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return Set.of();
    }

    @Override
    public Set<FlyResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return Set.of();
    }
}
