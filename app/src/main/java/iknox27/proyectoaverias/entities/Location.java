package iknox27.proyectoaverias.entities;

public class Location {
    private double lat;

    public double getLat() { return this.lat; }

    public void setLat(double lat) { this.lat = lat; }

    private double lon;

    public double getLon() { return this.lon; }

    public void setLon(double lon) { this.lon = lon; }

    public Location(double lat,double lon){
        this.lat = lat;
        this.lon = lon;
    }
}
