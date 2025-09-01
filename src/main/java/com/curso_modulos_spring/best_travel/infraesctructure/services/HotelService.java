package com.curso_modulos_spring.best_travel.infraesctructure.services;

import com.curso_modulos_spring.best_travel.api.models.responses.HotelResponse;
import com.curso_modulos_spring.best_travel.domain.entities.HotelEntity;
import com.curso_modulos_spring.best_travel.domain.repositories.HotelRepository;
import com.curso_modulos_spring.best_travel.infraesctructure.abstractservices.IHotelService;
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
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository)
    {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Set<HotelResponse> readByRating(Integer rating)
    {
        return this.hotelRepository.findByRatingGreaterThan(rating).stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Page<HotelResponse> readAll(Integer pageNumber, Integer pageSize, SortType sortType) {
        PageRequest pageRequest = null;

        switch (sortType)
        {
            case NONE ->  pageRequest = PageRequest.of(pageNumber, pageSize);
            case LOWER -> pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(FIELD_BY_SORT).descending());
        };

        return this.hotelRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public Set<HotelResponse> readLessPrice(BigDecimal price)
    {
        return this.hotelRepository.findByPriceLessThan(price).stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> readBetweenPrice(BigDecimal min, BigDecimal max)
    {
        return this.hotelRepository.findByPriceBetween(min, max).stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    private HotelResponse entityToResponse(HotelEntity hotelEntity)
    {
        var response = new HotelResponse();

        BeanUtils.copyProperties(hotelEntity, response);

        return response;
    }
}
