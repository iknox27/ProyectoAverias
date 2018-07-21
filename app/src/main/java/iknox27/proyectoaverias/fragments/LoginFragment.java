package iknox27.proyectoaverias.fragments;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.activities.UserActivity;
import iknox27.proyectoaverias.entities.User;


public class LoginFragment extends Fragment {
  CarouselView carouselView;
  private final int HOME = 16908332;
  private UserActivity mActivity;
  LoginInterface loginInterface;
  View rootView;
    int[] sampleImages = {R.drawable.slider6, R.drawable.slider1, R.drawable.slider3, R.drawable.slider4};
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
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
    setCarouselView();
    return rootView;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mActivity = (UserActivity) getActivity();
    loginInterface = (LoginInterface) context;
  }

  @OnClick(R.id.textNotRegister)
    public void register(){
      mActivity.setFragment(new RegisterFragment());
  }

    @OnClick(R.id.loginUser)
    public void login(){
      User user = new User();
      user.username = username.getText().toString();
      user.password = password.getText().toString();
      user.token = mActivity.utils.createkey(user.username + user.password);
      Log.d("RegisterKey",user.token);
      loginInterface.login(user);
    }

  ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }};

  public interface LoginInterface{
    void login(User user);
  }

  private void  setCarouselView(){
      carouselView = (CarouselView) rootView.findViewById(R.id.carouselView);
      carouselView.setPageCount(sampleImages.length);
      carouselView.setIndicatorVisibility(View.INVISIBLE);
      carouselView.setImageListener(imageListener);
  }
}
