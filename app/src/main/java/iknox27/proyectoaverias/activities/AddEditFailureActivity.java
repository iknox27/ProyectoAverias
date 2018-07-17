package iknox27.proyectoaverias.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

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
import iknox27.proyectoaverias.service.ConnectionImgurManager;
import iknox27.proyectoaverias.service.ImgurService;
import iknox27.proyectoaverias.utils.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditFailureActivity extends AppCompatActivity implements DetailsFailureFragment.InterfacePrueba, AddFailureFragment.FailureADDInterface {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    Failure failure;
    User user;
    Location location;
    int typeFab;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public Utils utils;
    ImgurService imgurService;
    @BindView(R.id.image)
    ImageView image;
    private File chosenFile;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    public Uri returnUri;
    Bitmap imageU;
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
        if (getIntent().getBooleanExtra("add", true)) {
            fab.setImageDrawable(getDrawable(R.drawable.ic_camera));
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
            Picasso.get().load(failure.imagen).into(image);

            setFragment(detailsFailureFragment);
            collapsingToolbarLayout.setTitle(failure.nombre);
            fab.setImageDrawable(getDrawable(R.drawable.ic_edit));
            typeFab = 0;
        }

        dynamicToolbarColor();
        toolbarTextAppernce();
    }


    @OnClick(R.id.fab)
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
            saveBitmap(imageU);
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
                    "Image has been saved in KidsPainting folder",
                    Toast.LENGTH_LONG).show();
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


    }

    private void dynamicToolbarColor() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.slider3);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
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
            Log.d("verLoc", l.getLatitude() + "");
        }
    }

    private Bundle setBundle(){
        Bundle b = new Bundle();
        b.putParcelable("fail",failure);
        b.putParcelable("responseUser",user);
        b.putParcelable("responseLoc",location);
        return b;
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = AddEditFailureActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }



    public void setImage(Bitmap imageFromCamera){
        image.setImageBitmap(imageFromCamera);
    }


    @Override
    public void a() {

    }

    @Override
    public void addI(String name, String type ,String des, String date) {
        imgurService = ConnectionImgurManager.obtenerServicio();
        imgurService.postImage(name,des,"","",MultipartBody.Part.createFormData(
                "image",
                chosenFile.getName(),
                RequestBody.create(MediaType.parse("image/*"), chosenFile)
        )).enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                Log.d("its", "its" + response.isSuccessful());
                if(response.isSuccessful()){
                    ImageResponse imageResponse = response.body();
                }

            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.d("its", "its" + t.getCause().getMessage());
            }
        });
    }
}
