package com.curso_modulos_spring.best_travel.infraesctructure.helpers;

import com.curso_modulos_spring.best_travel.domain.repositories.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class CustomerHelper
{
    private final CustomerRepository customerRepository;

    public CustomerHelper(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    public void increase(String customerId, Class<?> type)
    {
        var customerToUpdate = this.customerRepository.findById(customerId).orElseThrow();

        switch(type.getSimpleName())
        {
            case "TourService" -> customerToUpdate.setTotalTours(customerToUpdate.getTotalTours()+1);
            case "TicketService" -> customerToUpdate.setTotalFlights(customerToUpdate.getTotalFlights()+1);
            case "ReservationService" -> customerToUpdate.setTotalLodgings(customerToUpdate.getTotalLodgings()+1);
        }

        this.customerRepository.save(customerToUpdate);
    }
}
