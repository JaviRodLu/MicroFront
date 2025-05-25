package jrl.microFront.model;

import java.util.List;
import java.util.Objects;

public class Actor {
    private Integer idActor;
    private String nombre;
    private String fechaNacimiento;
    private String paisNacimiento;
    private List<Pelicula> peliculas;

    public Actor(Integer idActor, String nombre, String fechaNacimiento, String paisNacimiento, List<Pelicula> peliculas) {
        this.idActor = idActor;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.paisNacimiento = paisNacimiento;
        this.peliculas = peliculas;
    }

    public Actor() {
    }

    public Integer getIdActor() {
        return idActor;
    }

    public void setIdActor(Integer idActor) {
        this.idActor = idActor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public List<Pelicula> getPeliculas() {return peliculas;}

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    @Override
    public String toString() {
        return "Actor{" + "idActor: " + idActor + ", nombre: " + nombre + '\'' + ", fechaNacimiento: "
                + fechaNacimiento + '\'' + ", paisNacimiento: " + paisNacimiento + '\'' + ", peliculas: "
                + peliculas + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor actor = (Actor) o;
        return Objects.equals(idActor, actor.idActor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idActor);
    }

}
