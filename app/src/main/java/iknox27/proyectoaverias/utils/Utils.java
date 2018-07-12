package iknox27.proyectoaverias.utils;

import android.app.Activity;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import java.security.MessageDigest;


/**
 * Created by acamacho on 5/6/2018.
 */

public class Utils {

    Context context;
    MaterialDialog materialDialog;

    public Utils(){
        this.context = context;
    }

    public void showProgess(Context ctx,String title){
        materialDialog = new MaterialDialog.Builder(ctx)
                .content(title)
                .autoDismiss(false)
                .progress(true, 0)
                .show();
    }

    public void hideProgress(){
        if(materialDialog != null){
            materialDialog.dismiss();
        }
    }

    public  String createkey(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }




}
