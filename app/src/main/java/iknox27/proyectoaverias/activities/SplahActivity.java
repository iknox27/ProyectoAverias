package iknox27.proyectoaverias.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.helpers.PreferencesManager;

public class SplahActivity extends AppCompatActivity {
  private final int SPLASH_DISPLAY_LENGTH = 2000;
  PreferencesManager preferencesManager;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splah);
    preferencesManager = PreferencesManager.getInstance();
    new Handler().postDelayed(() -> {
      String token = preferencesManager.getStringValue(SplahActivity.this,"rememberUssr");
      Intent mainIntent;
      if(token.equals("")){
        mainIntent = new Intent(SplahActivity.this,UserActivity.class);
      }else{
        mainIntent = new Intent(SplahActivity.this,BreakDownsActivity.class);
      }
      startActivity(mainIntent);
      finish();
    }, SPLASH_DISPLAY_LENGTH);
  }
}
