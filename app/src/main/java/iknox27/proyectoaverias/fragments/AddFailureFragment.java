package iknox27.proyectoaverias.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.activities.AddEditFailureActivity;
import iknox27.proyectoaverias.activities.BreakDownsActivity;
import iknox27.proyectoaverias.activities.SplahActivity;
import iknox27.proyectoaverias.activities.UserActivity;
import iknox27.proyectoaverias.entities.Failure;


public class AddFailureFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    View rootView;
    FailureADDInterface interfaceAdd;
    AddEditFailureActivity addEditFailureActivity;
    private final int HOME = 16908332;

    @BindView(R.id.input_name_content)
    EditText inputNameContent;
    @BindView(R.id.input_type_content)
    EditText inputLayoutTypeContent;
    @BindView(R.id.input_description_content)
    EditText inputlayoutDescriptionContent;
    @BindView(R.id.txt_date_change)
    TextView txt_date_change;
    String myDate;
    public AddFailureFragment() {
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
        rootView =  inflater.inflate(R.layout.fragment_add_failure, container, false);
        ButterKnife.bind(this,rootView);
        return  rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Log.d("id", String.valueOf(item.getItemId()));
        switch (item.getItemId()) {
            case HOME:
                Intent mainIntent;
                mainIntent = new Intent(addEditFailureActivity,BreakDownsActivity.class);
                startActivity(mainIntent);
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
        interfaceAdd = (FailureADDInterface)context;
        addEditFailureActivity = (AddEditFailureActivity) getActivity();

    }

   @OnClick(R.id.dateAdd)
   public void dateChange(){
       Calendar now = Calendar.getInstance();
       DatePickerDialog dpd = DatePickerDialog.newInstance(
               AddFailureFragment.this,
               now.get(Calendar.YEAR),
               now.get(Calendar.MONTH),
               now.get(Calendar.DAY_OF_MONTH)
       );
       dpd.show(addEditFailureActivity.getFragmentManager(), "Datepickerdialog");
   }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        myDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        txt_date_change.setText(myDate);
    }

    @OnClick(R.id.addFailure)
    public void add(){
        interfaceAdd.addFailure(inputNameContent.getText().toString(),
                inputLayoutTypeContent.getText().toString(),
                inputlayoutDescriptionContent.getText().toString(),myDate);
    }

    public interface FailureADDInterface{
        void addFailure(String name, String type ,String des, String date);
    }
}
