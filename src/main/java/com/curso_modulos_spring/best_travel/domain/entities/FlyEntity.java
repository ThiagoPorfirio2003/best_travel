package com.curso_modulos_spring.best_travel.domain.entities;

import com.curso_modulos_spring.best_travel.util.AeroLine;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "fly")
public class FlyEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String originName;
    @Column(length = 20)
    private String destinyName;

    //Double corresponde al tipo de dato decimal de las columnas
    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;

    //BigDecimal corresponde al tipo de dato double precision
    //Se usa cuando necesitamos manejar mucha precision
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private AeroLine aeroLine;
}
