package iknox27.proyectoaverias.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.helpers.PreferencesManager;

public class SettingsActivity extends AppCompatActivity {
    PreferencesManager preferencesManager;

    @BindView(R.id.layout_sign_out)
    LinearLayout layoutSignout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        preferencesManager = PreferencesManager.getInstance();
    }


    @OnClick(R.id.layout_sign_out)
    public void signOut(){
        preferencesManager.saveString(SettingsActivity.this,"rememberUssr","");
        String token = preferencesManager.getStringValue(SettingsActivity.this,"rememberUssr");
        Intent mainIntent;
        if(token.equals("")){
            mainIntent = new Intent(SettingsActivity.this,UserActivity.class);
            startActivity(mainIntent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(),
                    "Ha ocurrido un error, intente mas tarde", Toast.LENGTH_LONG).show();
        }

    }

}
