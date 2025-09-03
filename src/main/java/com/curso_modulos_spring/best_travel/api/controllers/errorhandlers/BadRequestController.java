package com.curso_modulos_spring.best_travel.api.controllers.errorhandlers;

import com.curso_modulos_spring.best_travel.api.models.responses.BaseErrorResponse;
import com.curso_modulos_spring.best_travel.api.models.responses.ErrorResponse;
import com.curso_modulos_spring.best_travel.api.models.responses.ErrorsResponse;
import com.curso_modulos_spring.best_travel.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController
{
    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse idNotFoundHandler(IdNotFoundException exception)
    {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .build();

    }

    //Esta exception la arroja Spring
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse idNotFoundHandler(MethodArgumentNotValidException exception)
    {
        var errors = new ArrayList<String>();

        exception.getAllErrors().forEach(error->
        {
            errors.add(error.getDefaultMessage());
        });

        return ErrorsResponse.builder()
                .errors(errors)
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .build();

    }
}
