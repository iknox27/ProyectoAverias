package iknox27.proyectoaverias.helpers;

import android.content.Context;
import android.util.Log;


import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import iknox27.proyectoaverias.db.DataBaseHelper;
import iknox27.proyectoaverias.entities.User;

public class UserDBManager {

    DataBaseHelper bdHelper;
    private static UserDBManager singleton = new UserDBManager();

    private UserDBManager() { }

    public static UserDBManager getInstance() {
        if(singleton == null) {
            singleton = new UserDBManager();
        }
        return singleton;
    }

    public void startHelper(Context context){
        if (bdHelper == null) {
            bdHelper = new DataBaseHelper(context);
        }
    }

    public int getSizeUser(){
        ArrayList<User> contacts = new ArrayList<User>();
        try {
            Dao<User, Integer> userDao = bdHelper.getUserDao();
            contacts = (ArrayList<User>) userDao.queryForAll();
            if(contacts.size() == 0){
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  contacts.size();
    }

    public ArrayList<User> getUser(){
        ArrayList<User> contacts = new ArrayList<User>();
        try {
            Dao<User, Integer> userDao = bdHelper.getUserDao();
            contacts = (ArrayList<User>) userDao.queryForAll();
            //Si no se encontro ningun usuario, es porque no existe
            if(contacts.size() == 0){
                //Toast.makeText(MainActivity.this, "Ese usuario no existe!", Toast.LENGTH_SHORT).show();
                return contacts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  contacts;
    }

    public boolean getCurrentUserBoolean(String token){
        ArrayList<User> contacts = new ArrayList<User>();
        try {
            Dao<User, Integer> userDao = bdHelper.getUserDao();
            contacts = (ArrayList<User>) userDao.queryForEq("token",token);
            //Si no se encontro ningun usuario, es porque no existe
            if(contacts.size() == 0){
                //Toast.makeText(MainActivity.this, "Ese usuario no existe!", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }

    }


    public boolean getCurrentUserBooleanByUser(String user, String pass){
        ArrayList<User> contacts = new ArrayList<User>();
        try {
            Dao<User, Integer> userDao = bdHelper.getUserDao();
            contacts = (ArrayList<User>) userDao.queryForEq("username",user);
            //Si no se encontro ningun usuario, es porque no existe
            if(contacts.size() == 0){
                //Toast.makeText(MainActivity.this, "Ese usuario no existe!", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                if(contacts.get(0).password.equals(pass))
                    return true;
                else
                    return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }

    }

    public User getCurrentUser(String token){
        ArrayList<User> contacts = new ArrayList<User>();
        try {
            Dao<User, Integer> userDao = bdHelper.getUserDao();
            contacts = (ArrayList<User>) userDao.queryForEq("token",token);
            //Si no se encontro ningun usuario, es porque no existe
            if(contacts.size() > 0){
                //Toast.makeText(MainActivity.this, "Ese usuario no existe!", Toast.LENGTH_SHORT).show();
                return contacts.get(0);
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public boolean validateExistsUser(String usermname){
        ArrayList<User> contacts = new ArrayList<User>();
        try {
            Dao<User, Integer> userDao = bdHelper.getUserDao();
            contacts = (ArrayList<User>) userDao.queryForEq("username",usermname);
            if(contacts.size() > 0){
                return false;
            }else{
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return  true;
        }

    }


    public boolean saveUser(User c){
        try {
            Dao<User, Integer> userDao = bdHelper.getUserDao();
            userDao.create(c);
            return true;
        } catch (SQLException e) {
            Log.e("Create", e.getMessage().toString());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int id){
        try {
            Dao<User, Integer> userDao = bdHelper.getUserDao();
            userDao.deleteById(id);
            return true;
        } catch (SQLException e) {
            Log.e("Create", e.getMessage().toString());
            e.printStackTrace();
            return false;
        }
    }
}
