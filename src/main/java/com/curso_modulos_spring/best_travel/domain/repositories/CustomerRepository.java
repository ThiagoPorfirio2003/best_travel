package com.curso_modulos_spring.best_travel.domain.repositories;

import com.curso_modulos_spring.best_travel.domain.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

//Usamos CrudRepository porque solo vamos a usar 1 operacion
public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}
