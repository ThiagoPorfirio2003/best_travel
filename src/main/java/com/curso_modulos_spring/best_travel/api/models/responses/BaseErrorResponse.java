package com.curso_modulos_spring.best_travel.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseErrorResponse
{
    private String status;
    /*
        Se usa Integer en vez de int porque en caso de que no se pase un valor:
        - Integer: Su valor sera null
        - int: Su valor sera 0 lo cual puede provocar ciertos problemas
     */
    private Integer code;
}
