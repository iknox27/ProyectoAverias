package iknox27.proyectoaverias.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import iknox27.proyectoaverias.fragments.DetailsFailureFragment;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.entities.Failure;
import iknox27.proyectoaverias.fragments.AddFailureFragment;

public class AddEditFailureActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    Failure failure;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.fab)
    FloatingActionButton fab;

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
        if(getIntent().getBooleanExtra("add",true)){
            setFragment(new AddFailureFragment());
        }else{
            failure =  getIntent().getParcelableExtra("responseFail");
            DetailsFailureFragment detailsFailureFragment = new DetailsFailureFragment();
            Bundle b = new Bundle();
            b.putParcelable("fail",failure);
            b.putParcelable("responseUser",getIntent().getParcelableExtra("responseUser"));
            b.putParcelable("responseLoc",getIntent().getParcelableExtra("responseLoc"));
            detailsFailureFragment.setArguments(b);
            Picasso.get().load(failure.imagen).into(image);
            setFragment(detailsFailureFragment);
            collapsingToolbarLayout.setTitle(failure.nombre);
            fab.setVisibility(View.INVISIBLE);
        }

        dynamicToolbarColor();
        toolbarTextAppernce();
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


}
