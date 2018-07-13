package iknox27.proyectoaverias.entities;

public class Failure {
    public String id;
    public String nombre;
    public String tipo;
    private User usuario;
    public String fecha;
    public String descripcion;
    public String imagen;
    public Location ubicacion;

    public Failure(String id,String nombre,String tipo,String fecha,String descripcion, String imagen, Location location){
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.ubicacion = new Location(location.getLat(),location.getLon());
        this.setUsuario(new User());
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
}
