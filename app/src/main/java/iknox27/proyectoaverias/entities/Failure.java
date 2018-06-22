package iknox27.proyectoaverias.entities;

public class Failure {
    private String id;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    private String nombre;

    public String getNombre() { return this.nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    private String tipo;

    public String getTipo() { return this.tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    private User usuario;

    public User getUsuario() { return this.usuario; }

    public void setUsuario(User usuario) { this.usuario = usuario; }

    private String fecha;

    public String getFecha() { return this.fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }

    private String descripcion;

    public String getDescripcion() { return this.descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    private String imagen;

    public String getImagen() { return this.imagen; }

    public void setImagen(String imagen) { this.imagen = imagen; }

    private Location location;

    public Location getLocation() { return this.location; }

    public void setLocation(Location location) { this.location = location; }
}
