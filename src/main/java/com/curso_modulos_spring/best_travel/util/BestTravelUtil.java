package com.curso_modulos_spring.best_travel.util;

import java.time.LocalDateTime;
import java.util.Random;

public class BestTravelUtil
{
    //Las clases de utileria solo tienen miembros estaticos
    private static final Random random = new Random();

    public static LocalDateTime getRandomSoon()
    {
        //Me da un numero random entre 2 y 5
        var randomHours = random.nextInt(5-2) + 2;

        return LocalDateTime.now().plusHours(randomHours);
    }

    public static LocalDateTime getRandomLatter()
    {
        var randomHours = random.nextInt(12-6) + 6;

        return LocalDateTime.now().plusHours(randomHours);
    }
}
