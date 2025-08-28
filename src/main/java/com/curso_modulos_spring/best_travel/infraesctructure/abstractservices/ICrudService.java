package com.curso_modulos_spring.best_travel.infraesctructure.abstractservices;

/*
    Interfaz genererica para todos los servicios que ofrezcan metodo crud
    RQ: Request
    RP: Response
*/
public interface ICrudService<RQ, RP, ID>
{
    RP create(RQ request);
    RP read(ID id);
    RP update(RQ request, ID id);
    void delete(ID id);
}
