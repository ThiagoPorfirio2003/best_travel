package com.curso_modulos_spring.best_travel.infraesctructure.abstractservices;

import com.curso_modulos_spring.best_travel.api.models.requests.TicketRequest;
import com.curso_modulos_spring.best_travel.api.models.responses.TicketResponse;

import java.util.UUID;

public interface ITicketService extends ICrudService<TicketRequest, TicketResponse, UUID>
{

}
