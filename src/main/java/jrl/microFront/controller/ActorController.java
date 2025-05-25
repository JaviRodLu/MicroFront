package jrl.microFront.controller;

import jrl.microFront.model.Actor;
import jrl.microFront.paginator.PageRender;
import jrl.microFront.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/actores")
public class ActorController {

    @Autowired
    ActorService actorService;

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("nombre", "Nombre del actor");
        Actor actor = new Actor();
        model.addAttribute("actor", actor);
        return "actores/formActor";
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        return "actores/buscarActor";
    }

    @GetMapping("/idactor/{id}")
    public String buscarActorPorId(Model model, @PathVariable("id") Integer idActor) {
        Actor actor = actorService.buscarActorPorId(idActor);
        model.addAttribute("actor", actor);
        return "actores/formActor";
    }

    @GetMapping("/listado")
    public String buscarTodos(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Actor> listado = actorService.buscarTodos(pageable);
        PageRender<Actor> pageRender = new PageRender<Actor>("/actores/listado", listado);
        model.addAttribute("nombre", "Listado de actores");
        model.addAttribute("listadoActores", listado);
        model.addAttribute("page", pageRender);
        return "actores/listaActores";
    }

    @GetMapping("/nombre")
    public String buscarActoresPorNombre(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam("nombre") String nombre) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Actor> listado;
        if (nombre.equals("")) {
            listado = actorService.buscarTodos(pageable);
        } else {
            listado = actorService.buscarActorPorNombre(nombre, pageable);
        }
        PageRender<Actor> pageRender = new PageRender<Actor>("/actores/nombre?nombre=%s".formatted(nombre), listado);
        model.addAttribute("titulo", "Listado de actores por nombre");
        model.addAttribute("listadoActores", listado);
        model.addAttribute("page", pageRender);
        return "actores/listaActores";
    }

    @PostMapping("/guardar/")
    public String guardarActor(Model model, Actor actor, RedirectAttributes attributes) {
        actorService.guardarActor(actor);
        model.addAttribute("titulo", "Nuevo actor");
        attributes.addFlashAttribute("msg", "El actor ha sido guardado");
        return "redirect:/actores/listado";
    }

    @GetMapping("/editar/{idActor}")
    public String editarActor(Model model, @PathVariable("idActor") Integer idActor) {
        Actor actor = actorService.buscarActorPorId(idActor);
        model.addAttribute("titulo", "Editar actor");
        model.addAttribute("actor", actor);
        return "actores/formActor";
    }

    @GetMapping("/borrar/{idActor}")
    public String borrarActor(Model model, @PathVariable("idActor") Integer idActor, RedirectAttributes attributes) {
        actorService.eliminarActor(idActor);
        attributes.addFlashAttribute("msg", "El actor ha sido eliminado");
        return "redirect:/actores/listado";
    }

}
