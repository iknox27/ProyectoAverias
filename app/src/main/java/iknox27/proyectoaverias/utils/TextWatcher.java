package iknox27.proyectoaverias.utils;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import iknox27.proyectoaverias.R;

public class TextWatcher implements android.text.TextWatcher {

    Activity activity;
    EditText editText;
    View text;
    int id;

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
            case 1 : isValidEmail(charSequence.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void  isValidEmail(CharSequence target) {
        if(!TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded));
        }else{
            editText.setBackground(activity.getDrawable(R.drawable.edittext_rounded_error));
        }

    }
}
