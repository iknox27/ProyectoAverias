package iknox27.proyectoaverias.utils;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.activities.UserActivity;

public class TextWatcher implements android.text.TextWatcher {

    Activity activity;
    EditText editText;
    TextView text;
    int id;
    private static String NUMBER_REGEX = "\\d+";

   public TextWatcher(Activity activity, EditText editText, TextView text, int id){
        this.activity = activity;
        this.editText = editText;
        this.text = text;
        this.id = id;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        switch (id){
            case 1 : validateEmail(charSequence.toString()); break;
            case 2 : validateNumber(charSequence.toString()); break;
            case 3 : validateCardID(charSequence.toString()); break;
            case 4 : validateUsername(charSequence.toString()); break;
            case 5 : validatePassword(charSequence.toString()); break;
            case 6 : noEmpty(charSequence.toString()); break;
            case 7 : validateUsernameFromLogin(charSequence.toString()); break;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    private void  validateEmail(CharSequence target) {
        if(!TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
            text.setVisibility(View.INVISIBLE);
        }else{
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
            text.setText("Formato de correo incorrecto");
            text.setVisibility(View.VISIBLE);
        }

    }

    private void validateNumber(CharSequence target){
       if(target.toString().toLowerCase().matches(NUMBER_REGEX)){
           if(target.length() < 8){
               editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
               text.setText("Teléfono debe tener almenos 8 digitos");
               text.setVisibility(View.VISIBLE);
           }else{
               editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
               text.setVisibility(View.INVISIBLE);
           }
       }else{
           editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
           text.setText("Formato de teléfono incorrecto");
           text.setVisibility(View.VISIBLE);
       }
    }


    private void validateCardID(CharSequence target){
        if(target.toString().toLowerCase().matches(NUMBER_REGEX)){
            if(target.length() < 9){
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
                text.setText("Cédula debe tener almenos  9 digitos");
                text.setVisibility(View.VISIBLE);
            }else{
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
                text.setVisibility(View.INVISIBLE);
            }
        }else{
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
            text.setText("Formato de cédula incorrecto");
            text.setVisibility(View.VISIBLE);
        }
    }

    private void validateUsername(CharSequence target){
        if(target.length() == 0){
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
            text.setText("Usuario es requerido");
            text.setVisibility(View.VISIBLE);
        }else{
            if(((UserActivity) activity).getExiste(target.toString())){
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
                text.setVisibility(View.INVISIBLE);
            }else{
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
                text.setText("Usuario ya existente");
                text.setVisibility(View.VISIBLE);
            }
        }
    }

    private void validateUsernameFromLogin(CharSequence target){
        if(target.length() == 0){
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
            text.setText("Usuario es requerido");
            text.setVisibility(View.VISIBLE);
        }else{
            if(((UserActivity) activity).getExiste(target.toString())){
                Log.d("etre","s");
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
                text.setText("Usuario no existe");
                text.setVisibility(View.VISIBLE);
            }else{
                Log.d("etre","l");
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
                text.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void validatePassword(CharSequence target){
            if(target.length() < 8){
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
                text.setText("Contraseña debe tener almenos  8 digitos");
                text.setVisibility(View.VISIBLE);
            }else{
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
                text.setVisibility(View.INVISIBLE);
            }

    }

    private void noEmpty(CharSequence target){
        if(target.length()==0){
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
            text.setText("Este campo es requerido");
            text.setVisibility(View.VISIBLE);
        }else{
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
            text.setVisibility(View.INVISIBLE);
        }

    }



}
//