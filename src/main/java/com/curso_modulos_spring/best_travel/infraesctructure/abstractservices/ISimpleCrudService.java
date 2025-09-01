package com.curso_modulos_spring.best_travel.infraesctructure.abstractservices;

public interface ISimpleCrudService<RQ, RP, ID>
{
    RP create(RQ request);
    RP read(ID id);
    void delete(ID id);
}
