package jrl.microFront.service;

import jrl.microFront.model.Critica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CriticaService {
    Page<Critica> buscarTodas(Pageable pageable);
    Page<Critica> buscarCriticasPorIdPelicula(Integer idPelicula, Pageable pageable);
    Critica buscarCriticasPorId(Integer idCritica);
    String guardarCritica(Critica cr);
    void eliminarCritica(Integer idCritica);
}
