package iknox27.proyectoaverias.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.entities.Failure;
import iknox27.proyectoaverias.entities.Location;
import iknox27.proyectoaverias.utils.Utils;


public class MapFragment extends Fragment implements GoogleMap.OnMapLongClickListener {

    View rootView;
    MapView mMapView;
    private GoogleMap googleMap;
    Utils utils;
    MapInterface mapInterface;
    public MapFragment() {
        // Required empty public constructor
        utils = new Utils();
    }

    public interface MapInterface{
        void sendCreateNewFailure(LatLng lng,boolean itsFromMap);
        void sendViewDatails(Failure fail,boolean itsFromMap );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this,rootView);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        final ArrayList<Failure> failureList = getArguments().getParcelableArrayList("lista");
        try {
            MapsInitializer.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                boolean success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                getActivity(), R.raw.style));
                android.location.Location bestLocation = utils.getLastKnownLocation(getActivity());
                LatLng myCuurrentLocation = new LatLng(bestLocation.getLatitude(), bestLocation.getLongitude());;
                CameraPosition cameraPosition = new CameraPosition.Builder().target(myCuurrentLocation).zoom(10).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.setOnMapLongClickListener(MapFragment.this);
                for(int i = 0 ; i < failureList.size() ; i++) {
                    createMarker(failureList.get(i).ubicacion.getLat(), failureList.get(i).ubicacion.getLon(), failureList.get(i).nombre, failureList.get(i).descripcion, R.drawable.ic_place);
                }

            }
        });

        return rootView;
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {

        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mapInterface = (MapInterface)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        Log.d("MAPapruabn", "a");
        new MaterialDialog.Builder(getActivity())
                .title("Crear avería")
                .content("¿Desea crear una nueva avería en este punto?")
                .positiveText("Crear")
                .negativeText("Cancelar")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mapInterface.sendCreateNewFailure(latLng,true);
                    }
                })
                .show();
    }
}
