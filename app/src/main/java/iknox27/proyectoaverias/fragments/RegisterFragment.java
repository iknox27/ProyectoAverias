package iknox27.proyectoaverias.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.activities.UserActivity;
import iknox27.proyectoaverias.entities.User;

public class RegisterFragment extends Fragment {

  private final int HOME = 16908332;
  private UserActivity mActivity;
  View rootView;
  RegisterInterface registerInterface;

  @BindView(R.id.name)
  EditText name;
  @BindView(R.id.email)
  EditText email;
  @BindView(R.id.phoneNumber)
  EditText phoneNumber;
  @BindView(R.id.userid)
  EditText userid;
  @BindView(R.id.usernameRegister)
  EditText usernameRegister;
  @BindView(R.id.passwordRegister)
  EditText passwordRegister;

  public RegisterFragment() {
    // Required empty public constructor
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    rootView =  inflater.inflate(R.layout.fragment_register, container, false);
    ButterKnife.bind(this,rootView);
    mActivity.getSupportActionBar().show();
    mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    mActivity.setTitle("Registro de Usuario");
    return rootView;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mActivity = (UserActivity) getActivity();
    registerInterface = (RegisterInterface)context;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case HOME:
        mActivity.setFragment(new LoginFragment());
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @OnClick(R.id.addUser)
  public void descargar(){
    User user = new User();
    user.nombre =  name.getText().toString();
    user.correo = email.getText().toString();
    user.tel = phoneNumber.getText().toString();
    user.cedula = userid.getText().toString();
    user.username = usernameRegister.getText().toString();
    user.password =  passwordRegister.getText().toString();
    user.token = mActivity.utils.createkey(user.username + user.password);
    registerInterface.register(user);
  }

  public interface RegisterInterface{
    void register(User user);
  }

}
