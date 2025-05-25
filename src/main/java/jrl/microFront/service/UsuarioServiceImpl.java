package jrl.microFront.service;

import jrl.microFront.model.Usuario;
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
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    RestTemplate template;

    String url = "http://localhost:8090/api/usersreviews/usuarios";

    @Override
    public Page<Usuario> buscarTodos(Pageable pageable) {
        Usuario[] usuarios = template.getForObject(url, Usuario[].class);
        List<Usuario> listaUsuarios = Arrays.asList(usuarios);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Usuario> lista;

        if (listaUsuarios.size() < startItem) {
            lista = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listaUsuarios.size());
            lista = listaUsuarios.subList(startItem, toIndex);
        }

        Page<Usuario> pagina = new PageImpl<Usuario>(lista, PageRequest.of(currentPage, pageSize), listaUsuarios.size());
        return pagina;
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        Usuario usuario = template.getForObject(url+"/"+idUsuario, Usuario.class);
        return usuario;
    }

    @Override
    public Usuario buscarUsuarioPorNombreUsuario(String username) {
        Usuario usuario = template.getForObject(url+"/nombreUsuario/"+username, Usuario.class);
        return usuario;
    }

    @Override
    public Usuario buscarUsuarioPorCorreo(String email) {
        Usuario usuario = template.getForObject(url+"/correo/"+email, Usuario.class);
        return usuario;
    }

    @Override
    public Usuario login(String correo, String clave) {
        Usuario usuario = template.getForObject(url + "/login/" + correo + "/" + clave, Usuario.class);
        return usuario;
    }

    @Override
    public void hacerCritica(Integer idUsuario, Integer idPelicula) {
        template.getForObject(url+"/hacerCritica/"+idUsuario+"/"+idPelicula, String.class);
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        if (usuario.getIdUsuario() != null && usuario.getIdUsuario() > 0) {
            template.put(url, usuario);
        } else {
            usuario.setIdUsuario(0);
            template.postForObject(url, usuario, String.class);
        }
    }

    @Override
    public void eliminarUsuario(Integer idUsuario) {
        template.delete(url+"/"+idUsuario);
    }

}
