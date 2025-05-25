package jrl.microFront.service;

import jrl.microFront.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    RestTemplate template;

    String url = "http://localhost:8090/api/actores";

    @Override
    public Page<Actor> buscarTodos(Pageable pageable) {
        Actor[] actores = template.getForObject(url, Actor[].class);
        List<Actor> listaActores = Arrays.asList(actores);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Actor> lista;

        if (listaActores.size() < startItem) {
            lista = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listaActores.size());
            lista = listaActores.subList(startItem, toIndex);
        }

        Page<Actor> pagina = new PageImpl<>(lista, PageRequest.of(currentPage, pageSize), listaActores.size());
        return pagina;
    }

    @Override
    public Actor buscarActorPorId(Integer idActor) {
        Actor actor = template.getForObject(url + "/" + idActor, Actor.class);
        return actor;
    }

    @Override
    public Page<Actor> buscarActorPorNombre(String nombre, Pageable pageable) {
        Actor[] actores = template.getForObject(url + "/nombre/" + nombre, Actor[].class);
        List<Actor> listaActores = Arrays.asList(actores);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Actor> lista;

        if (listaActores.size() < startItem) {
            lista = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listaActores.size());
            lista = listaActores.subList(startItem, toIndex);
        }

        Page<Actor> pagina = new PageImpl<>(lista, PageRequest.of(currentPage, pageSize), listaActores.size());
        return pagina;
    }

    @Override
    public void guardarActor(Actor actor) {
        if (actor.getIdActor() != null && actor.getIdActor() > 0) {
            template.put(url, actor);
        } else {
            actor.setIdActor(0);
            template.postForObject(url, actor, String.class);
        }
    }

    @Override
    public void eliminarActor(Integer idActor) {
        template.delete(url + "/" + idActor);
    }

}
