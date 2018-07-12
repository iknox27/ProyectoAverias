package iknox27.proyectoaverias.entities;

import com.j256.ormlite.field.DatabaseField;

public class User {

    @DatabaseField(generatedId = true, columnName = "user_id", canBeNull = false)
    public int userId;
    @DatabaseField(columnName = "name", canBeNull = false)
    public String name;
    @DatabaseField(columnName = "email", canBeNull = false)
    public String email;
    @DatabaseField(columnName = "phone", canBeNull = false)
    public String phone;
    @DatabaseField(columnName = "card_id", canBeNull = false)
    public String card_id;
    @DatabaseField(columnName = "username", canBeNull = false)
    public String username;
    @DatabaseField(columnName = "password", canBeNull = false)
    public String password;
    @DatabaseField(columnName = "token", canBeNull = false)
    public String token;

    public User(){}
}
