package com.curso_modulos_spring.best_travel.infraesctructure.abstractservices;

import com.curso_modulos_spring.best_travel.api.models.requests.ReservationRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.ReservationResponse;

import java.util.UUID;

public interface IReservationService extends ICrudService<ReservationRequest, ReservationResponse, UUID>
{
}
