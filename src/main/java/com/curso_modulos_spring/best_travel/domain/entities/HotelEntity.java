package com.curso_modulos_spring.best_travel.domain.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "hotel")
public class HotelEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String adress;

    private Integer rating;

    private BigDecimal price;

}
