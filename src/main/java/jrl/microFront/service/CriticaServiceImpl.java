package jrl.microFront.service;

import jrl.microFront.model.Pelicula;
import jrl.microFront.model.Usuario;
import jrl.microFront.model.Critica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CriticaServiceImpl implements CriticaService {
    @Autowired
    RestTemplate template;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PeliculaService peliculaService;

    String url = "http://localhost:8090/api/usersreviews/criticas";

    @Override
    public Page<Critica> buscarTodas(Pageable pageable) {
        Critica[] criticas = template.getForObject(url, Critica[].class);
        List<Critica> listaCriticas = Arrays.asList(criticas);

        int tamanoPagina = pageable.getPageSize();
        int paginaActual = pageable.getPageNumber();
        int inicial = paginaActual * tamanoPagina;
        List<Critica> lista;

        if (listaCriticas.size() < inicial) {
            lista = Collections.emptyList();
        } else {
            int toIndex = Math.min(inicial + tamanoPagina, listaCriticas.size());
            lista = listaCriticas.subList(inicial, toIndex);
        }

        Page<Critica> pagina = new PageImpl<>(lista, PageRequest.of(paginaActual, tamanoPagina), listaCriticas.size());
        return pagina;
    }

    @Override
    public Page<Critica> buscarCriticasPorIdPelicula(Integer idPelicula, Pageable pageable) {
        Critica[] criticas = template.getForObject(url + "/pelicula/" + idPelicula, Critica[].class);
        List<Critica> listaCriticas = Arrays.asList(criticas);

        int tamanoPagina = pageable.getPageSize();
        int paginaActual = pageable.getPageNumber();
        int inicial = paginaActual * tamanoPagina;
        List<Critica> lista;

        if (listaCriticas.size() < inicial) {
            lista = Collections.emptyList();
        } else {
            int toIndex = Math.min(inicial + tamanoPagina, listaCriticas.size());
            lista = listaCriticas.subList(inicial, toIndex);
        }

        Page<Critica> pagina = new PageImpl<>(lista, PageRequest.of(paginaActual, tamanoPagina), listaCriticas.size());
        return pagina;
    }

    @Override
    public Critica buscarCriticasPorId(Integer idCritica) {
        Critica critica = template.getForObject(url+"/"+idCritica, Critica.class);
        return critica;
    }

    @Override
    public String guardarCritica(Critica critica) {
        String resultado = "";
        if (critica.getIdCritica() != null && critica.getIdCritica() > 0) {
            return "No se puede modificar una crítica";
        } else {
            Usuario usuario = usuarioService.buscarUsuarioPorId(critica.getUsuario().getIdUsuario());
            Pelicula pelicula = peliculaService.buscarPeliculaPorId(critica.getIdPelicula());
            if (usuario == null) {
                resultado = "Usuario no encontrado";
            } else {
                //Comprobar que ya no ha realizado una crítica de esa película
                resultado = "Usuario encontrado";
                List<Critica> criticas = usuario.getCriticas();
                for (Critica cr : criticas) {
                    if (cr.getIdPelicula().equals(pelicula.getIdPelicula())) {
                        return "La película ya ha sido reseñada por el usuario";
                    }
                }
                usuarioService.hacerCritica(usuario.getIdUsuario(), pelicula.getIdPelicula());
                critica.setIdPelicula(pelicula.getIdPelicula());
                critica.setUsuario(usuario);
                critica.setFecha(new Date());
                critica.setNota(critica.getNota());
                template.postForObject(url, critica, String.class);
                resultado += "Reseña guardada";
            }
        }
        return resultado;
    }

    @Override
    public void eliminarCritica(Integer idReview) {
        template.delete(url+"/"+idReview);
    }

}
