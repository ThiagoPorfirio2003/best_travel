package com.curso_modulos_spring.best_travel.api.controllers.errorhandlers;

import com.curso_modulos_spring.best_travel.api.models.responses.BaseErrorResponse;
import com.curso_modulos_spring.best_travel.api.models.responses.ErrorResponse;
import com.curso_modulos_spring.best_travel.util.exceptions.ForbiddenCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenCustomerHandler
{
    @ExceptionHandler(ForbiddenCustomerException.class)
    public BaseErrorResponse handleForbiddenCustomer(ForbiddenCustomerException exception)
    {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.FORBIDDEN.value())
                .status(HttpStatus.FORBIDDEN.name())
                .build();

    }
}
