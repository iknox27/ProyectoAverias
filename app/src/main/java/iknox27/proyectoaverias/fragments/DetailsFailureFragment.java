package iknox27.proyectoaverias.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.entities.Failure;
import iknox27.proyectoaverias.entities.Location;
import iknox27.proyectoaverias.entities.User;


public class DetailsFailureFragment extends Fragment {

    View rootView;
    Failure failure;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txt_type_change)
    TextView txt_type_change;
    @BindView(R.id.txt_date_change)
    TextView txt_date_change;
    @BindView(R.id.txt_user_change)
    TextView txt_user_change;
    @BindView(R.id.txt_description_change)
    TextView txt_description_change;
    @BindView(R.id.txt_location_change)
    TextView txt_location_change;
    InterfacePrueba prueba;
    public DetailsFailureFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_details_failure, container, false);
        ButterKnife.bind(this,rootView);
        failure = getArguments().getParcelable("fail");
        Location location = getArguments().getParcelable("responseLoc");
        User user = getArguments().getParcelable("responseUser");
        if(failure != null){
            txtTitle.setText(failure.id);
            txt_type_change.setText(failure.tipo);
                    txt_date_change.setText(failure.fecha);
            txt_user_change.setText(user.nombre);
                    txt_description_change.setText(failure.descripcion);
         txt_location_change.setText("Latitud: " + location.getLat() +" Longitud: " + location.getLon());
        }
        return rootView;
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        prueba = (InterfacePrueba) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public  interface  InterfacePrueba{
        void a();
    }

}
