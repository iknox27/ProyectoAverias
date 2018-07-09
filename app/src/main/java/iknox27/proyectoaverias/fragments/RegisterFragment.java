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

import butterknife.ButterKnife;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.activities.UserActivity;

public class RegisterFragment extends Fragment {

  private final int HOME = 16908332;
  private UserActivity mActivity;
  View rootView;
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

}
