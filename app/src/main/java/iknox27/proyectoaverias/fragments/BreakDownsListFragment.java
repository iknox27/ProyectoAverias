package iknox27.proyectoaverias.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.adapters.BreaksListAdapter;
import iknox27.proyectoaverias.entities.Failure;
import iknox27.proyectoaverias.service.ConnectionServiceManager;
import iknox27.proyectoaverias.service.FailureService;
import iknox27.proyectoaverias.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BreakDownsListFragment extends Fragment {

    View rootView;
    @BindView(R.id.recycler_list) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;
    BreaksListAdapter adapter;
    FailureService failureService;
    Utils utils;
    public BreakDownsListFragment() {
        // Required empty public constructor
        utils = new Utils();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtenerAverias();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.fragment_break_downs_list, container, false);
        ButterKnife.bind(this,rootView);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void obtenerAverias(){
        utils.showProgess(getActivity(),"Cargando Aveerías");
        failureService = ConnectionServiceManager.obtenerServicio();
        failureService.obtenerListaDeAverias().enqueue(new Callback<List<Failure>>() {
            @Override
            public void onResponse(Call<List<Failure>> call, Response<List<Failure>> response) {
                Log.d("bien","bien");
                if(response.isSuccessful() && response.body().size() > 0){
                    attachRecycler(response.body());
                }else{

                }
                utils.hideProgress();
            }

            @Override
            public void onFailure(Call<List<Failure>> call, Throwable t) {
                Log.d("mal","mal");
                utils.hideProgress();
            }
        });
    }


    public void attachRecycler(List<Failure> response){
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(5);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(rootView.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new BreaksListAdapter(response,recyclerView,rootView.getContext(),getActivity());
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void fabClicked(){
        Toast.makeText(getActivity(),
                "Toast por defecto", Toast.LENGTH_SHORT).show();
    }
}
