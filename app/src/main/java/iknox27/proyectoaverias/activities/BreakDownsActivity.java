package iknox27.proyectoaverias.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.adapters.TabPagerAdapter;
import iknox27.proyectoaverias.entities.Failure;
import iknox27.proyectoaverias.entities.Location;
import iknox27.proyectoaverias.entities.User;
import iknox27.proyectoaverias.fragments.BreakDownsListFragment;
import iknox27.proyectoaverias.fragments.MapFragment;
import iknox27.proyectoaverias.service.ConnectionServiceManager;
import iknox27.proyectoaverias.service.FailureService;
import iknox27.proyectoaverias.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreakDownsActivity extends AppCompatActivity implements BreakDownsListFragment.BreakListInterface, MapFragment.MapInterface {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager pager;
    FailureService failureService;
    Utils utils;
    Bundle b;
    ArrayList<Failure> array;
    Failure f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breaks);
        ButterKnife.bind(this);
        utils = new Utils();
        b = new Bundle();
        this.obtenerAverias();

    }

    private void initTab(){
        tabLayout.addTab(tabLayout.newTab().setText("Lista"));
        tabLayout.addTab(tabLayout.newTab().setText("Mapa"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void initPager(){
        final TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),b);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(BreakDownsActivity.this, SettingsActivity.class );
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void goToAddEdit() {
        Intent i = new Intent(BreakDownsActivity.this, AddEditFailureActivity.class);
        i.putExtra("add",true);
        startActivity(i);
        finish();
    }

    private void obtenerAverias(){
        utils.showProgess(this,"Cargando Averías");
        failureService = ConnectionServiceManager.obtenerServicio();
        failureService.obtenerListaDeAverias().enqueue(new Callback<List<Failure>>() {
            @Override
            public void onResponse(@NonNull Call<List<Failure>> call, Response<List<Failure>> response) {
                array = (ArrayList<Failure>) response.body();
                b.putParcelableArrayList("lista", array);
                initTab();
                initPager();
                utils.hideProgress();
            }

            @Override
            public void onFailure(Call<List<Failure>> call, Throwable t) {
                Log.d("malqq","mal");
                utils.hideProgress();
            }
        });
    }

    public void getAllDataFromFailure(String id){
        utils.showProgess(this,"Obteniendo datos de Avería");
        failureService = ConnectionServiceManager.obtenerServicio();
        failureService.obtenerDetallesDePost(id).enqueue(new Callback<Failure>() {
            @Override
            public void onResponse(Call<Failure> call, Response<Failure> response) {
                if(response.isSuccessful()){
                    Intent i = new Intent(BreakDownsActivity.this, AddEditFailureActivity.class);
                    i.putExtra("add",false);
                    Bundle b = new Bundle();
                    f = response.body();
                    User u = f.getUsuario();
                    Location loc = f.ubicacion;
                    i.putExtra("responseFail",f);
                    i.putExtra("responseUser",u);
                    i.putExtra("responseLoc",loc);
                    utils.hideProgress();
                    startActivity(i);
                    finish();
                }else{
                    Log.d("mal","mal");
                    utils.hideProgress();
                }

            }

            @Override
            public void onFailure(Call<Failure> call, Throwable t) {

            }
        });
    }

    @Override
    public void sendCreateNewFailure(LatLng lng,boolean itsFromMap) {
        Intent i = new Intent(BreakDownsActivity.this, AddEditFailureActivity.class);
        i.putExtra("add",true);

        Location locFromMap = new Location(lng.latitude,lng.longitude);
        i.putExtra("itsFromMap",itsFromMap);
        i.putExtra("latlng",locFromMap);
        startActivity(i);
        finish();
    }

    @Override
    public void sendViewDatails(String id) {
        getAllDataFromFailure(id);
    }
}
