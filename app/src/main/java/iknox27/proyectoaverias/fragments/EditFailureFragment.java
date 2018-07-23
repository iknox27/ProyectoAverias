package iknox27.proyectoaverias.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.activities.AddEditFailureActivity;
import iknox27.proyectoaverias.activities.BreakDownsActivity;
import iknox27.proyectoaverias.entities.Failure;
import iknox27.proyectoaverias.entities.Location;
import iknox27.proyectoaverias.entities.User;


public class EditFailureFragment extends Fragment {


    View rootView;
    AddEditFailureActivity addEditFailureActivity;
    private final int HOME = 16908332;
    EditInterface editInterface;
    Failure failure;
    User user;
    Location location;

    @BindView(R.id.input_name_edit)
    EditText inputNameEdit;
    @BindView(R.id.input_type_edit)
    EditText inputLayoutTypeEdit;
    @BindView(R.id.input_description_edit)
    EditText inputlayoutDescriptionEdit;
    @BindView(R.id.txt_date_edit)
    TextView txtDateEdit;

    String myDate;

    public EditFailureFragment() {
        // Required empty public constructor
    }

    public  interface  EditInterface{
        void editFailure(String id, Failure failureEdited);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_edit_failure, container, false);
        ButterKnife.bind(this,rootView);
        if (savedInstanceState == null){
            failure = getArguments().getParcelable("fail");
            user = getArguments().getParcelable("responseUser");
            location = getArguments().getParcelable("responseLoc");

            inputNameEdit.setText(failure.nombre);
            inputlayoutDescriptionEdit.setText(failure.descripcion);
            inputLayoutTypeEdit.setText(failure.tipo);
            txtDateEdit.setText(failure.fecha);
            failure.setUsuario(user);
            failure.setUbicacion(location);
        }
        return  rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case HOME:
                addEditFailureActivity.setDataFromDit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        addEditFailureActivity = (AddEditFailureActivity) getActivity();
        editInterface = (EditInterface)context;
    }

    @OnClick(R.id.edit_failure)
    public void editFailure(){
        editInterface.editFailure("sd",failure);
    }

}
