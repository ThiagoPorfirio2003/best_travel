package com.curso_modulos_spring.best_travel.util.exceptions;

public class ForbiddenCustomerException extends RuntimeException
{
    public ForbiddenCustomerException(String customerId)
    {
        super(String.format("The customer with ID: %s is blocked", customerId));
    }
}
