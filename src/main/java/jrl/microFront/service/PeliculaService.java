package jrl.microFront.service;

import jrl.microFront.model.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeliculaService {
    Page<Pelicula> buscarTodas(Pageable pageable);

    Pelicula buscarPeliculaPorId(Integer idPelicula);

    Page<Pelicula> buscarPeliculasPorTitulo(String titulo, Pageable pageable);

    Page<Pelicula> buscarPeliculasPorGenero(String genero, Pageable pageable);

    void guardarPelicula(Pelicula pelicula);

    void eliminarPelicula(Integer idPelicula);

}
