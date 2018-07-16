package iknox27.proyectoaverias.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
    private double lat;

    protected Location(Parcel in) {
        lat = in.readDouble();
        lon = in.readDouble();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public double getLat() { return this.lat; }

    public void setLat(double lat) { this.lat = lat; }

    private double lon;

    public double getLon() { return this.lon; }

    public void setLon(double lon) { this.lon = lon; }

    public Location(double lat,double lon){
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(lat);
        parcel.writeDouble(lon);
    }
}
