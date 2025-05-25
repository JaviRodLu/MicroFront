package jrl.microFront.service;

import jrl.microFront.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {
    Page<Usuario> buscarTodos(Pageable pageable);
    Usuario buscarUsuarioPorId(Integer idUsuario);
    Usuario buscarUsuarioPorNombreUsuario(String username);
    Usuario buscarUsuarioPorCorreo(String email);
    Usuario login(String email, String password);
    void hacerCritica(Integer idUsuario, Integer idPelicula);
    void guardarUsuario(Usuario usuario);
    void eliminarUsuario(Integer idUsuario);
}
