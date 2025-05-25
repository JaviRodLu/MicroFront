package jrl.microFront.model;

import java.util.Date;

public class Critica {
    private Integer idCritica;
    private Usuario usuario;
    private Integer idPelicula;
    private Integer nota;
    private Date fecha;

    public Critica() {}

    public Critica(Integer idCritica, Usuario usuario, Integer idPelicula, Integer nota, Date fecha) {
        this.idCritica = idCritica;
        this.usuario = usuario;
        this.idPelicula = idPelicula;
        this.nota = nota;
        this.fecha = fecha;
    }

    public Integer getIdCritica() {
        return idCritica;
    }

    public void setIdCritica(Integer idCritica) {
        this.idCritica = idCritica;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Critica{" +
                "idCritica=" + idCritica +
                ", usuario=" + usuario +
                ", idPelicula=" + idPelicula +
                ", nota=" + nota +
                ", fecha=" + fecha +
                '}';
    }

}
