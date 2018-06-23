package iknox27.proyectoaverias.helpers;

import android.content.Context;
import android.content.SharedPreferences;



public class PreferencesManager {

    private static SharedPreferences mPreferences;
    private static final String PREF_FILE = "app.preferences";
    public String ARG_FONTSIZE = "arg.fontSize";
    public  String ARG_BACKGROUNDCOLOR = "arg.backgroundColor";
    public  String ARG_BACKGROUNDCOLOR2 = "arg.backgroundColor2";
    public String ARG_SHOWHIDE = "arg.remember";
    public  String ARG_COLUMNS = "arg.cols";
    private static PreferencesManager singleton = new PreferencesManager( );

    private PreferencesManager() { }

    public static PreferencesManager getInstance( ) {
        if(singleton == null) {
            singleton = new PreferencesManager();
        }
        return singleton;
    }


    public void saveString(Context ctx,String key, String value){
        if(mPreferences == null){
            mPreferences = ctx.getSharedPreferences(PREF_FILE, ctx.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveInt(Context ctx,String key, int value){
        if(mPreferences == null){
            mPreferences = ctx.getSharedPreferences(PREF_FILE, ctx.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void saveBoolean(Context ctx,String key, boolean value){
        if(mPreferences == null){
            mPreferences = ctx.getSharedPreferences(PREF_FILE, ctx.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getStringValue(Context ctx,String key){
        if(mPreferences == null){
            mPreferences = ctx.getSharedPreferences(PREF_FILE, ctx.MODE_PRIVATE);
        }
        return mPreferences.getString(key, "");
    }

    public int getIntValue(Context ctx,String key){
        if(mPreferences == null){
            mPreferences = ctx.getSharedPreferences(PREF_FILE, ctx.MODE_PRIVATE);
        }
        return mPreferences.getInt(key, 1);
    }

    public  boolean getBoolean(Context ctx,String key){
        if(mPreferences == null){
            mPreferences = ctx.getSharedPreferences(PREF_FILE, ctx.MODE_PRIVATE);
        }
        return mPreferences.getBoolean(key, false);
    }
}
