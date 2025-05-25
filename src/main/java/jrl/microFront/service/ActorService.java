package jrl.microFront.service;

import jrl.microFront.model.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActorService {
    Page<Actor> buscarTodos(Pageable pageable);

    Actor buscarActorPorId(Integer idActor);

    Page<Actor> buscarActorPorNombre(String nombre, Pageable pageable);

    void guardarActor(Actor actor);

    void eliminarActor(Integer idActor);

}
