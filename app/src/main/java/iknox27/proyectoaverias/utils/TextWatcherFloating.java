package iknox27.proyectoaverias.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import iknox27.proyectoaverias.R;

public class TextWatcherFloating implements android.text.TextWatcher {

    Activity activity;
    EditText editText;
    TextView text;
    int id;

    public TextWatcherFloating(Activity activity, EditText editText, TextView text, int id){
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
        noEmpty(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void noEmpty(CharSequence target){
        if(target.length()==0){
            setError();
            text.setText("Este campo es requerido");
            text.setVisibility(View.VISIBLE);
        }else{
            setValid();
            text.setVisibility(View.INVISIBLE);
        }

    }

    private void setError(){
        Drawable drawable = editText.getBackground();
        drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        editText.setBackground(drawable);
    }

    private void setValid(){
        Drawable drawable = editText.getBackground();
        drawable.setColorFilter(ResourcesCompat.getColor(activity.getResources(), R.color.darkGray, null), PorterDuff.Mode.SRC_ATOP);
        editText.setBackground(drawable);
    }

}
