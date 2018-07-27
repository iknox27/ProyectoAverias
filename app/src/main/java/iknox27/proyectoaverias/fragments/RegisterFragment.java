package iknox27.proyectoaverias.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.activities.UserActivity;
import iknox27.proyectoaverias.entities.User;
import iknox27.proyectoaverias.utils.TextWatcher;

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

  @BindView(R.id.name_text_error)
  TextView nameTextError;
  @BindView(R.id.email_text_error)
  TextView emailTextError;
  @BindView(R.id.phoneNumber_text_error)
  TextView phoneNumberTextError;
  @BindView(R.id.userid_text_error)
  TextView useridTextError;
  @BindView(R.id.usernameRegister_text_error)
  TextView usernameRegisterTextError;
  @BindView(R.id.passwordRegister_text_error)
  TextView passwordRegisterTextError;


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

    email.addTextChangedListener(new TextWatcher(mActivity,email,emailTextError,1));
    phoneNumber.addTextChangedListener(new TextWatcher(mActivity,phoneNumber,phoneNumberTextError,2));
    userid.addTextChangedListener(new TextWatcher(mActivity,userid,useridTextError,3));
    usernameRegister.addTextChangedListener(new TextWatcher(mActivity,usernameRegister,usernameRegisterTextError,4));
    passwordRegister.addTextChangedListener(new TextWatcher(mActivity,passwordRegister,passwordRegisterTextError,5));
    name.addTextChangedListener(new TextWatcher(mActivity,name,nameTextError,6));

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
  public void registerUser(){
    User user = new User();
    boolean hasvalidForm = true;
    if(name.getText().length() == 0){
      hasvalidForm = false;
    }
    if (TextUtils.isEmpty(email.getText().toString()) && !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
      hasvalidForm = false;
    }
    if(phoneNumber.getText().length() < 8){
      hasvalidForm = false;
    }

    if(userid.getText().toString().length() < 9){
      hasvalidForm = false;
    }

    if (usernameRegister.getText().length() == 0 && mActivity.getExiste(usernameRegister.getText().toString())){
      hasvalidForm = false;
    }

    if (passwordRegister.getText().length() < 8){
      hasvalidForm = false;
    }


    if(hasvalidForm){
      user.nombre =  name.getText().toString();
      user.correo = email.getText().toString();
      user.tel = phoneNumber.getText().toString();
      user.cedula = userid.getText().toString();
      user.username = usernameRegister.getText().toString();
      user.password =  passwordRegister.getText().toString();
      user.token = mActivity.utils.createkey(user.username + user.password);
      Log.d("RegisterKey",user.token);
      registerInterface.register(user);
    }


  }

  public interface RegisterInterface{
    void register(User user);
  }

}
