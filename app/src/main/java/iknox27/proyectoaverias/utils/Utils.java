package iknox27.proyectoaverias.utils;

import android.app.Activity;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;


/**
 * Created by acamacho on 5/6/2018.
 */

public class Utils {

    Activity context;
    MaterialDialog materialDialog;

    public Utils(Activity context){
        this.context = context;
    }

    public void showProgess(String title){
        materialDialog = new MaterialDialog.Builder(context)
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



}
