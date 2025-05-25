package jrl.microFront.model;

public class Rol {
    private Integer idRol;
    private String privilegio;

    public Rol() {}

    public Rol(Integer idRol, String privilegio) {
        this.idRol = idRol;
        this.privilegio = privilegio;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "idRol=" + idRol +
                ", privilegio='" + privilegio + '\'' +
                '}';
    }
}
