package iknox27.proyectoaverias.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

public class User implements Parcelable{

    @DatabaseField(generatedId = true, columnName = "user_id", canBeNull = false)
    public int userId;
    @DatabaseField(columnName = "name", canBeNull = false)
    public String nombre;
    @DatabaseField(columnName = "email", canBeNull = false)
    public String correo;
    @DatabaseField(columnName = "phone", canBeNull = false)
    public String tel;
    @DatabaseField(columnName = "card_id", canBeNull = false)
    public String cedula;
    @DatabaseField(columnName = "username", canBeNull = false)
    public String username;
    @DatabaseField(columnName = "password", canBeNull = false)
    public String password;
    @DatabaseField(columnName = "token", canBeNull = false)
    public String token;

    public User(){}

    protected User(Parcel in) {
        userId = in.readInt();
        nombre = in.readString();
        correo = in.readString();
        tel = in.readString();
        cedula = in.readString();
        username = in.readString();
        password = in.readString();
        token = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(nombre);
        parcel.writeString(correo);
        parcel.writeString(tel);
        parcel.writeString(cedula);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(token);
    }
}
