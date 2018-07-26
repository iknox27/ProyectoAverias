package iknox27.proyectoaverias.utils;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.activities.UserActivity;

public class TextWatcher implements android.text.TextWatcher {

    Activity activity;
    EditText editText;
    View text;
    int id;
    private static String NUMBER_REGEX = "\\d+";

   public TextWatcher(Activity activity,EditText editText /*View text*/, int id){
        this.activity = activity;
        this.editText = editText;
        //this.text = text;
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
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    public void  validateEmail(CharSequence target) {
        if(!TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
        }else{
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
        }

    }

    public void validateNumber(CharSequence target){
       if(target.toString().toLowerCase().matches(NUMBER_REGEX)){
           if(target.length() < 8){
               editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
           }else{
               editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
           }
       }else{
           editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
       }
    }


    public void validateCardID(CharSequence target){
        if(target.toString().toLowerCase().matches(NUMBER_REGEX)){
            if(target.length() < 9){
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
            }else{
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
            }
        }else{
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
        }
    }

    public void validateUsername(CharSequence target){
        if(target.length() == 0){
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
        }else{
            if(((UserActivity) activity).getExiste(target.toString())){
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
            }else{
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
            }
        }
    }

    public void validatePassword(CharSequence target){
            if(target.length() < 8){
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
            }else{
                editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
            }

    }

}
//https://stackoverflow.com/questions/26574328/changing-edittext-bottom-line-color-with-appcompat-v7