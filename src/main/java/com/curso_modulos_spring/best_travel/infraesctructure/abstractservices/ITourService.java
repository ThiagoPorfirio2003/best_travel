package com.curso_modulos_spring.best_travel.infraesctructure.abstractservices;


import com.curso_modulos_spring.best_travel.api.models.requests.TourRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TourResponse;

import java.util.UUID;

public interface ITourService extends ISimpleCrudService<TourRequest, TourResponse, Long>
{
    void removeTicket(Long tourId, UUID ticketId);
    UUID addTicket(Long tourId, Long flyId);

    void removeReservation(Long tourId, UUID reservationId);
    UUID addReservation(Long tourId, UUID reservationId);
}
