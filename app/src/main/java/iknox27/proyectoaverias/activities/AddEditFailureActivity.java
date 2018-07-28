package iknox27.proyectoaverias.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iknox27.proyectoaverias.entities.ImageResponse;
import iknox27.proyectoaverias.entities.Location;
import iknox27.proyectoaverias.entities.User;
import iknox27.proyectoaverias.fragments.DetailsFailureFragment;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.entities.Failure;
import iknox27.proyectoaverias.fragments.AddFailureFragment;
import iknox27.proyectoaverias.fragments.EditFailureFragment;
import iknox27.proyectoaverias.helpers.PreferencesManager;
import iknox27.proyectoaverias.helpers.UserDBManager;
import iknox27.proyectoaverias.service.ConnectionImgurManager;
import iknox27.proyectoaverias.service.ConnectionServiceManager;
import iknox27.proyectoaverias.service.FailureService;
import iknox27.proyectoaverias.service.ImgurService;
import iknox27.proyectoaverias.utils.Utils;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditFailureActivity extends AppCompatActivity implements  AddFailureFragment.FailureADDInterface, EditFailureFragment.EditInterface {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    Failure failure;
    User user;
    Location location;
    int typeFab;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MY_WRITE_LOCATION = 98;
    private static final int EDIT_DATA = 2131230745;
    private static final int DELETE_DATA = 2131230743;
    public Utils utils;
    ImgurService imgurService;
    PreferencesManager preferenceManager;
    UserDBManager userDBManager;
    @BindView(R.id.image)
    ImageView image;
    private File chosenFile;
    @BindView(R.id.fabutton)
    FloatingActionButton fab;
    public Uri returnUri;
    Bitmap imageU;
    FailureService failureService;
    FabSpeedDial fabSpeedDial;
    boolean isFromMap = false;
    Location latLngFromMap;
    boolean hasChangedImage = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_failure);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        utils = new Utils();
        failureService = ConnectionServiceManager.obtenerServicio();
        userDBManager = UserDBManager.getInstance();
        userDBManager.startHelper(getApplicationContext());
        preferenceManager = PreferencesManager.getInstance();
        if (getIntent().getBooleanExtra("add", true)) {
            isFromMap = getIntent().getBooleanExtra("itsFromMap",false);
            latLngFromMap = getIntent().getParcelableExtra("latlng");
            fab.setImageDrawable(getDrawable(R.drawable.ic_camera));
            fab.setVisibility(View.VISIBLE);
            fabSpeedDial = (FabSpeedDial) findViewById(R.id.fabd);
            fabSpeedDial.setVisibility(View.INVISIBLE);
            collapsingToolbarLayout.setTitle("Agregar avería");
            typeFab = 1;
            setFragment(new AddFailureFragment());
            validateLocationPermissions();

        } else {
            failure = getIntent().getParcelableExtra("responseFail");
            user = getIntent().getParcelableExtra("responseUser");
            location = getIntent().getParcelableExtra("responseLoc");
            DetailsFailureFragment detailsFailureFragment = new DetailsFailureFragment();
            detailsFailureFragment.setArguments(setBundle());
            if(failure.imagen != null && !failure.imagen.equals("") ){
                Picasso.get().load(failure.imagen).into(image);
            }else{
                Picasso.get().load(R.drawable.noimage).into(image);
            }

            fab.setVisibility(View.INVISIBLE);
            fabSpeedDial = (FabSpeedDial) findViewById(R.id.fabd);
            fabSpeedDial.setVisibility(View.VISIBLE);
            fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
                @Override
                public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                    return true;
                }

                @Override
                public boolean onMenuItemSelected(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case EDIT_DATA  :
                            Log.d("idValor", menuItem.getItemId()+"");
                            collapsingToolbarLayout.setTitle("Editar Avería");
                            fab.setVisibility(View.VISIBLE);
                            fabSpeedDial.setVisibility(View.INVISIBLE);
                            EditFailureFragment editFailureFragment = new EditFailureFragment();
                            editFailureFragment.setArguments(setBundle());
                            setFragment(editFailureFragment);
                            typeFab = 1;
                            break;
                        case DELETE_DATA :
                            askToDetele();
                            Log.d("idValor", menuItem.getItemId()+"");
                            break;
                    }
                    return true;
                }

                @Override
                public void onMenuClosed() {

                }
            });
            setFragment(detailsFailureFragment);
            collapsingToolbarLayout.setTitle(failure.nombre);
            typeFab = 0;
        }
        dynamicToolbarColor();
        toolbarTextAppernce();
    }


    @OnClick(R.id.fabutton)
    public void clickFab() {
        if (typeFab == 0) {
            fab.setImageDrawable(getDrawable(R.drawable.ic_camera));
            collapsingToolbarLayout.setTitle("Editar avería");
            EditFailureFragment edit = new EditFailureFragment();
            edit.setArguments(setBundle());
            setFragment(edit);
        } else {
            openCamera();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            this.returnUri = data.getData();
            imageU = (Bitmap) data.getExtras().get("data");
            saveImage();
            setImage(imageU);
        }
    }

    public void saveBitmap(Bitmap bmp) {
        String _time = "";
        Calendar cal = Calendar.getInstance();
        int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);
        _time = "image_" + hourofday + "" + minute + "" + second + ""
                + millisecond + ".png";
        String file_path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/averias";
        try {
            File dir = new File(file_path);
            if (!dir.exists())
                dir.mkdirs();
            chosenFile = new File(dir, _time);
            FileOutputStream fOut = new FileOutputStream(chosenFile);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
            Toast.makeText(getApplicationContext(),
                    "Imagen se guardado!",

                    Toast.LENGTH_LONG).show();
            hasChangedImage = true;
        } catch (Exception e) {
            Log.e("error in saving image", e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_CAMERA_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.utils.configChooserCamera(this.getApplicationContext(), this, 1);
        }

        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }

        if(requestCode == MY_WRITE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            saveBitmap(imageU);
        }
    }


    private void dynamicToolbarColor() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.slider3);
        Palette.from(bitmap).generate(palette -> {
            collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
            collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
        });
    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentadd, fragment);
        fragmentTransaction.commit();
    }

    private void openCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (utils.verifyPermissions(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                this.utils.configChooserCamera(this.getApplicationContext(), this, 1);
            else
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        } else {
            this.utils.configChooserCamera(this.getApplicationContext(), this, 1);
        }
    }

    private void validateLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (utils.verifyPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            else {
               android.location.Location l =  utils.getLastKnownLocation(this);
               Log.d("verLoc", l.getLatitude() + "");
            }
        } else {
            android.location.Location l =  utils.getLastKnownLocation(this);
//            Log.d("verLoc", l.getLatitude() + "");
        }
    }

    public Bundle setBundle(){
        Bundle b = new Bundle();
        b.putParcelable("fail",failure);
        b.putParcelable("responseUser",user);
        b.putParcelable("responseLoc",location);
        return b;
    }

    public void setImage(Bitmap imageFromCamera){
        image.setImageBitmap(imageFromCamera);
    }

    @Override
    public void addFailure(final String name, final String type ,final String des, final String date) {
        utils.showProgess(this,"Creando Avería");
        android.location.Location loc = utils.getLastKnownLocation(AddEditFailureActivity.this);
        User u = userDBManager.getCurrentUser(preferenceManager.getStringValue(AddEditFailureActivity.this,"rememberUssr"));
        Location location = isFromMap ? latLngFromMap : new Location(loc.getLatitude(),loc.getLongitude());
        String id = utils.createkey(date+name+type);
        Failure fail = new Failure(id,name,type,date,des,"",location,u);
        if(!hasChangedImage){
            addNewFailure(fail);
        }
        else{
            uploadImage(fail,1);
        }

    }

    private void uploadImage(Failure f, int whereTo){
        imgurService = ConnectionImgurManager.obtenerServicio();
        imgurService.postImage(f.nombre,f.descripcion,"","",MultipartBody.Part.createFormData(
                "image",
                chosenFile.getName(),
                RequestBody.create(MediaType.parse("image/*"), chosenFile)
        )).enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                Log.d("its", "its" + response.isSuccessful());
                if(response.isSuccessful()){
                    ImageResponse imageResponse = response.body();
                    if(imageResponse != null){
                        f.imagen = imageResponse.data.link;
                    }
                    switch (whereTo){
                        case 1 : addNewFailure(f);  break;
                        case 2 : editCurrentFailure(f);  break;
                    }
                }else{
                    utils.hideProgress();
                    Toast.makeText(getApplicationContext(),
                            "Ha ocurrido un error subiendo la imagen, intente mas tarde", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.d("its", "its" + t.getCause().getMessage());
                Toast.makeText(getApplicationContext(),
                        "Ha ocurrido un error subiendo la imagen, intente mas tarde", Toast.LENGTH_LONG).show();
                utils.hideProgress();
            }
        });
    }

    public void addNewFailure(Failure f){
        failureService.crearAveria(f).enqueue(new Callback<Failure>() {
            @Override
            public void onResponse(Call<Failure> call, Response<Failure> response) {
                utils.hideProgress();
                if(response.isSuccessful() && response.code() == 200){
                    Toast.makeText(getApplicationContext(),
                            "Avería creada exitosamente", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(AddEditFailureActivity.this, BreakDownsActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Ha ocurrido un error agregando la averia, intente mas tarde", Toast.LENGTH_LONG).show();
                }
                Log.d("respone", "respone" + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Failure> call, Throwable t) {
                utils.hideProgress();
                Toast.makeText(getApplicationContext(),
                        "Ha ocurrido un error agregando la averia, intente mas tarde", Toast.LENGTH_LONG).show();
                Log.d("fail", "fail" + t.getCause().getMessage());
            }
        });
    }


    private void askToDetele(){
        new MaterialDialog.Builder(this)
                .title("Eliminar Avería")
                .content("Desea eliminar la averia"+ failure.nombre)
                .positiveText("Aceptar")
                .negativeText("Cancelar")
                .onPositive((dialog, which) -> {
                    utils.showProgess(AddEditFailureActivity.this,"Eliminando avería");
                    deleteFailure(failure.id);
                })
                .show();
    }

    private void deleteFailure(String id){
        failureService.eliminarAveriaPorId(id).enqueue(new Callback<Failure>() {
            @Override
            public void onResponse(Call<Failure> call, Response<Failure> response) {
                if(response.isSuccessful()&& response.code() == 200){
                    utils.hideProgress();
                    Toast.makeText(getApplicationContext(),
                            "Avería eliminada exitosamente", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(AddEditFailureActivity.this, BreakDownsActivity.class);
                    startActivity(i);
                    finish();

                }else{
                    utils.hideProgress();
                    Toast.makeText(getApplicationContext(),
                            "Ha ocurrido un error eliminando la averia, intente mas tarde", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Failure> call, Throwable t) {
                utils.hideProgress();
                Toast.makeText(getApplicationContext(),
                        "Ha ocurrido un error eliminando la averia, intente mas tarde", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void  saveImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (utils.verifyPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                saveBitmap(imageU);
            else
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_WRITE_LOCATION);
        } else {
            saveBitmap(imageU);
        }
    }

    @Override
    public void editFailure(Failure failureEdited) {
        utils.showProgess(this,"Editando Avería");
        if(!hasChangedImage){
            editCurrentFailure(failureEdited);
        }else{
            uploadImage(failureEdited,2);
        }
    }

    private void editCurrentFailure(Failure failureEdited){
        failureService.editarAveriaPorId(failureEdited.id,failureEdited).enqueue(new Callback<Failure>() {
            @Override
            public void onResponse(Call<Failure> call, Response<Failure> response) {
                utils.hideProgress();
                if(response.isSuccessful() && response.code() == 200){
                    Toast.makeText(getApplicationContext(),
                            "Avería editada exitosamente", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(AddEditFailureActivity.this, BreakDownsActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Ha ocurrido un error editando la averia, intente mas tarde", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Failure> call, Throwable t) {
                utils.hideProgress();
                Toast.makeText(getApplicationContext(),
                        "Ha ocurrido un error editando la averia, intente mas tarde", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void setDataFromDit(){
        DetailsFailureFragment detailsFailureFragment = new DetailsFailureFragment();
        detailsFailureFragment.setArguments(setBundle());
        collapsingToolbarLayout.setTitle(failure.nombre);
        fab.setVisibility(View.INVISIBLE);
        fabSpeedDial.setVisibility(View.VISIBLE);
        setFragment(detailsFailureFragment);
    }
}
