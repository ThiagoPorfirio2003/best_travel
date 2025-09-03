package com.curso_modulos_spring.best_travel.infraesctructure.abstractservices;

import com.curso_modulos_spring.best_travel.util.enums.SortType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Set;

//R es un tipo de dato de response
public interface ICatalogService<R>
{
    //El campo que usamos para ordenar los resultados
    String FIELD_BY_SORT = "price";

    Page<R> readAll(Integer pageNumber, Integer pageSize, SortType sortType);

    //Traer todos los que tengan un precio menor al recibido
    Set<R> readLessPrice(BigDecimal price);

    Set<R> readBetweenPrice(BigDecimal min, BigDecimal max);
}
