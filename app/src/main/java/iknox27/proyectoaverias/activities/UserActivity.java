package iknox27.proyectoaverias.activities;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.entities.User;
import iknox27.proyectoaverias.fragments.LoginFragment;
import iknox27.proyectoaverias.fragments.RegisterFragment;
import iknox27.proyectoaverias.helpers.PreferencesManager;
import iknox27.proyectoaverias.helpers.UserDBManager;
import iknox27.proyectoaverias.utils.Utils;

public class UserActivity extends AppCompatActivity implements RegisterFragment.RegisterInterface{
  private static final long SPLASH_SCREEN_DELAY = 3000;
  public Utils utils;
  UserDBManager userDBManager;
  PreferencesManager preferenceManager;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);
    setFragment(new LoginFragment());
    utils = new Utils();
    userDBManager = UserDBManager.getInstance();
    userDBManager.startHelper(getApplicationContext());
    preferenceManager = PreferencesManager.getInstance();
  }

  @Override
  public void register(final User user) {
    utils.showProgess(this,"Creando Usuario");

    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        userDBManager.saveUser(user);
        int size = userDBManager.getSizeUser();
        utils.hideProgress();
        if(size > 0){

          preferenceManager.saveString(UserActivity.this,"token",user.token);

        }else{
        }
      }
    };
    Timer timer = new Timer();
    timer.schedule(task, SPLASH_SCREEN_DELAY);


  }

  public void setFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction =
            fragmentManager.beginTransaction();
    fragmentTransaction.replace(android.R.id.content, fragment);
    fragmentTransaction.commit();
  }
}
