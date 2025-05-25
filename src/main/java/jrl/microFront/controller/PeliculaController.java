package jrl.microFront.controller;

import jrl.microFront.model.Pelicula;
import jrl.microFront.paginator.PageRender;
import jrl.microFront.service.PeliculaService;
import jrl.microFront.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    PeliculaService peliculaService;

    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/uploads/{file:.+}")
    public ResponseEntity<Resource> verFichero(@PathVariable String file) {
        Resource resource = null;
        try {
            resource = uploadFileService.load(file);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; file=\"" + resource.getFilename() + "\"").body(resource);
    }

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model) {
        return "home";
    }

    @GetMapping(value = "/ver/{id}")
    public String ver(Model m, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        Pelicula p = peliculaService.buscarPeliculaPorId(id);
        m.addAttribute("pelicula", p);
        m.addAttribute("titulo", p.getTitulo());
        return "peliculas/verPelicula";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        Pelicula pelicula = new Pelicula();
        model.addAttribute("titulo", "Nueva Pelicula");
        model.addAttribute("pelicula", pelicula);
        return "peliculas/formPelicula";
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        return "peliculas/buscarPelicula";
    }

    @GetMapping("/idpelicula/{id}")
    public String buscarPeliculaPorId(Model model, @PathVariable("id") Integer idPelicula) {
        Pelicula pelicula = peliculaService.buscarPeliculaPorId(idPelicula);
        model.addAttribute("pelicula", pelicula);
        return "peliculas/formPelicula";
    }

    @GetMapping("/listado")
    public String buscarTodas(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Pelicula> listado = peliculaService.buscarTodas(pageable);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/peliculas/listado", listado);
        model.addAttribute("titulo", "Listado de Peliculas");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listaPeliculas";
    }

    @GetMapping("/titulo")
    public String buscarPeliculasPorTitulo(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam("titulo") String titulo) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Pelicula> listado;
        if (titulo.equals("")) {
            listado = peliculaService.buscarTodas(pageable);
        } else {
            listado = peliculaService.buscarPeliculasPorTitulo(titulo, pageable);
        }
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/peliculas/titulo?titulo=%s".formatted(titulo), listado);
        model.addAttribute("titulo", "Listado de Peliculas por Título");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listaPeliculas";
    }

    @GetMapping("/genero")
    public String buscarPeliculasPorGenero(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam("genero") String genero) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Pelicula> listado;
        if (genero.equals("")) {
            listado = peliculaService.buscarTodas(pageable);
        } else {
            listado = peliculaService.buscarPeliculasPorGenero(genero, pageable);
        }
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/peliculas/genero?genero=%s".formatted(genero), listado);
        model.addAttribute("titulo", "Listado de Peliculas por Género");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listaPeliculas";
    }

    /*@PostMapping("/guardar/")
    public String guardarPelicula(Model model, Pelicula pelicula, @RequestParam("file") MultipartFile portada, RedirectAttributes attributes) {
        if (!portada.isEmpty()) {
            if (pelicula.getIdPelicula() != null && pelicula.getIdPelicula() > 0 && pelicula.getPortada() != null
                    && pelicula.getPortada().length() > 0) {
                uploadFileService.delete(pelicula.getPortada());
            }
            String file = null;
            try {
                file = uploadFileService.copy(portada);
            } catch (IOException e) {
                e.printStackTrace();
            }
            attributes.addFlashAttribute("message", "Fichero subido correctamente: '" + file + "'");
            pelicula.setPortada(file);
        }
        peliculaService.guardarPelicula(pelicula);
        model.addAttribute("titulo", "Nueva Película");
        attributes.addFlashAttribute("msg", "La película ha sido guardada");
        return "redirect:/peliculas/listado";
    }*/

    @PostMapping("/guardar/")
    public String guardarPelicula(Model model, Pelicula pelicula, RedirectAttributes attributes) {
        peliculaService.guardarPelicula(pelicula);
        model.addAttribute("titulo", "Nueva Película");
        attributes.addFlashAttribute("msg", "La película ha sido guardada");
        return "redirect:/peliculas/listado";
    }

    @GetMapping("/editar/{idPelicula}")
    public String editarPelicula(Model model, @PathVariable("idPelicula") Integer idPelicula) {
        Pelicula pelicula = peliculaService.buscarPeliculaPorId(idPelicula);
        model.addAttribute("titulo", "Editar Película");
        model.addAttribute("pelicula", pelicula);
        return "peliculas/formPelicula";
    }

    @GetMapping("/borrar/{idPelicula}")
    public String eliminarPelicula(Model model, @PathVariable("idPelicula") Integer idPelicula, RedirectAttributes attributes) {
        Pelicula p = peliculaService.buscarPeliculaPorId(idPelicula);
        if (uploadFileService.delete(p.getPortada())) {
            attributes.addFlashAttribute("msg", "Fichero " + p.getPortada() + " eliminado con éxito");
        }
        peliculaService.eliminarPelicula(idPelicula);
        attributes.addFlashAttribute("msg", "La película ha sido eliminada");
        return "redirect:/peliculas/listado";
    }

}
