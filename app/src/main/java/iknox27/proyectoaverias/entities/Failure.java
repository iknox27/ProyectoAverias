package iknox27.proyectoaverias.entities;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Failure implements Parcelable {
    public String id;
    public String nombre;
    public String tipo;
    public User usuario;
    public String fecha;
    public String descripcion;
    public String imagen;
    public Location ubicacion;

    public Failure(String id,String nombre,String tipo,String fecha,String descripcion, String imagen, Location location, User u){
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.ubicacion = new Location(location.getLat(),location.getLon());
        if(u!= null)
            this.usuario = u;
        else
            this.usuario = new User();
    }

    protected Failure(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        tipo = in.readString();
        fecha = in.readString();
        descripcion = in.readString();
        imagen = in.readString();
    }

    public static final Creator<Failure> CREATOR = new Creator<Failure>() {
        @Override
        public Failure createFromParcel(Parcel in) {
            return new Failure(in);
        }

        @Override
        public Failure[] newArray(int size) {
            return new Failure[size];
        }
    };

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nombre);
        parcel.writeString(tipo);
        parcel.writeString(fecha);
        parcel.writeString(descripcion);
        parcel.writeString(imagen);
    }
}
