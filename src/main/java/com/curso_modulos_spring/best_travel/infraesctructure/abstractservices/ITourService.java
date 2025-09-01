package com.curso_modulos_spring.best_travel.infraesctructure.abstractservices;


import com.curso_modulos_spring.best_travel.api.models.requests.TourRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TourResponse;

import java.util.UUID;

public interface ITourService extends ISimpleCrudService<TourRequest, TourResponse, Long>
{
    void removeTicket(UUID ticketId, Long tourId);
    UUID addTicket(Long flyId, Long tourId);

    void removeReservation(UUID reservationId, Long tourId);
    UUID addReservation(UUID reservationId, Long tourId);
}
