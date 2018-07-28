package iknox27.proyectoaverias.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import iknox27.proyectoaverias.utils.TextWatcherFloating;
import iknox27.proyectoaverias.utils.Utils;


public class EditFailureFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    View rootView;
    AddEditFailureActivity addEditFailureActivity;
    private final int HOME = 16908332;
    EditInterface editInterface;
    Failure failure;
    User user;
    Location location;
    String myDate;
    Utils utils;

    @BindView(R.id.input_name_edit)
    EditText inputNameEdit;
    @BindView(R.id.input_type_edit)
    EditText inputLayoutTypeEdit;
    @BindView(R.id.input_description_edit)
    EditText inputlayoutDescriptionEdit;
    @BindView(R.id.txt_date_edit)
    TextView txtDateEdit;


    @BindView(R.id.name_text_error)
    TextView nameTextError;
    @BindView(R.id.type_text_error)
    TextView typeTextError;
    @BindView(R.id.des_text_error)
    TextView desTextError;

    public EditFailureFragment() {
        // Required empty public constructor
        utils = new Utils();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        myDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        txtDateEdit.setText(myDate);
    }

    public  interface  EditInterface{
        void editFailure(Failure failureEdited);
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
        inputNameEdit.addTextChangedListener(new TextWatcherFloating(addEditFailureActivity,inputNameEdit,nameTextError,1));
        inputLayoutTypeEdit.addTextChangedListener(new TextWatcherFloating(addEditFailureActivity,inputLayoutTypeEdit,typeTextError,2));
        inputlayoutDescriptionEdit.addTextChangedListener(new TextWatcherFloating(addEditFailureActivity,inputlayoutDescriptionEdit,desTextError,3));
        if (savedInstanceState == null){
            failure = getArguments().getParcelable("fail");
            user = getArguments().getParcelable("responseUser");
            location = getArguments().getParcelable("responseLoc");

            inputNameEdit.setText(failure.nombre);
            inputlayoutDescriptionEdit.setText(failure.descripcion);
            inputLayoutTypeEdit.setText(failure.tipo);
            txtDateEdit.setText(failure.fecha);
            myDate = failure.fecha;
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

    @OnClick(R.id.date_edit)
    public void dateChange(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                EditFailureFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(addEditFailureActivity.getFragmentManager(), "Datepickerdialog");
    }

    @OnClick(R.id.edit_failure)
    public void editFailure(){
        if(validateEditableFields()){
            failure.nombre = inputNameEdit.getText().toString();
            failure.descripcion = inputlayoutDescriptionEdit.getText().toString();
            failure.tipo = inputLayoutTypeEdit.getText().toString();
            String myDate2 = !txtDateEdit.getText().toString().equals("") ? txtDateEdit.getText().toString() : new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
            failure.fecha = myDate2;
            editInterface.editFailure(failure);
        }

    }

    private boolean validateEditableFields(){
        boolean hasvalidForm = true;
        if(inputNameEdit.getText().length() == 0){
            hasvalidForm = false;
            utils.setError(inputNameEdit);
            nameTextError.setText(addEditFailureActivity.getResources().getString(R.string.required));
            nameTextError.setVisibility(View.VISIBLE);
        }
        if (inputLayoutTypeEdit.getText().length() == 0){
            hasvalidForm = false;
            utils.setError(inputNameEdit);
            typeTextError.setText(addEditFailureActivity.getResources().getString(R.string.required));
            typeTextError.setVisibility(View.VISIBLE);
        }
        if(inputlayoutDescriptionEdit.getText().length() == 0){
            hasvalidForm = false;
            utils.setError(inputlayoutDescriptionEdit);
            desTextError.setText(addEditFailureActivity.getResources().getString(R.string.required));
            desTextError.setVisibility(View.VISIBLE);
        }
        return hasvalidForm;
    }



}
