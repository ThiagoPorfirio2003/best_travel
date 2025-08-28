package com.curso_modulos_spring.best_travel.infraesctructure.services;

import com.curso_modulos_spring.best_travel.api.models.responses.FlyResponse;
import com.curso_modulos_spring.best_travel.domain.entities.FlyEntity;
import com.curso_modulos_spring.best_travel.domain.repositories.FlyRepository;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.IFlyService;
import com.curso_modulos_spring.best_travel.util.SortType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        return this.flyRepository.selectOriginDestiny(origin,destiny).stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Page<FlyResponse> readAll(Integer pageNumber, Integer pageSize, SortType sortType)
    {
        PageRequest pageRequest = null;

        switch (sortType)
        {
            case NONE ->  pageRequest = PageRequest.of(pageNumber, pageSize);
            case LOWER -> pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(FIELD_BY_SORT).descending());
        };

        return this.flyRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return this.flyRepository.selectLessPrice(price).stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return this.flyRepository.selectBetweenPrice(min,max).stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    private FlyResponse entityToResponse(FlyEntity flyEntity)
    {
        var flyResponse = new FlyResponse();

        BeanUtils.copyProperties(flyEntity, flyResponse);

        return flyResponse;
    }

    //private Set<FlyResponse>
}
