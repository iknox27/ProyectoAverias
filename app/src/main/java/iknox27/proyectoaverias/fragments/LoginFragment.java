package iknox27.proyectoaverias.fragments;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.activities.UserActivity;


public class LoginFragment extends Fragment {

  private final int HOME = 16908332;
  private UserActivity mActivity;
  View rootView;

  @BindView(R.id.textNotRegister)
  TextView txt;

  public LoginFragment() {
    // Required empty public constructor
  }



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    rootView =  inflater.inflate(R.layout.fragment_login, container, false);
    ButterKnife.bind(this,rootView);
    mActivity.getSupportActionBar().hide();
    //mActivity.setTitle("Formularios");
    return rootView;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mActivity = (UserActivity) getActivity();
  }

  @OnClick(R.id.textNotRegister)
    public void register(){
      mActivity.setFragment(new RegisterFragment());
  }



}
