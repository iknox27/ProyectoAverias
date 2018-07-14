package iknox27.proyectoaverias.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import iknox27.proyectoaverias.R;
import iknox27.proyectoaverias.entities.Failure;

public class BreaksListAdapter extends RecyclerView.Adapter<BreaksListAdapter.BreaksListViewHolder>{

     List<Failure> myBreaks;
     RecyclerView recyclerView;
     Context mContext;
     Activity activity;
    public BreaksListAdapter(List<Failure> fails, RecyclerView recyclerView, Context context, Activity activity){
        myBreaks = fails;
        this.recyclerView = recyclerView;
        this.mContext = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public BreaksListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.failure_items, parent, false);
        BreaksListViewHolder pvh = new BreaksListViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BreaksListViewHolder holder, int position) {
        holder.name.setText(myBreaks.get(position).nombre);
        if(!myBreaks.get(position).imagen.contains(".jpg") && !myBreaks.get(position).imagen.contains(".png")){
            Picasso.get().load(R.drawable.noimage).into(holder.img);
        }else{
            Picasso.get().load(myBreaks.get(position).imagen).into(holder.img);
        }
    }

    public void setList(List<Failure> myBreaks){
        this.myBreaks= myBreaks;
    }

    @Override
    public int getItemCount() {
        return myBreaks.size();
    }

    public static class BreaksListViewHolder extends RecyclerView.ViewHolder {


        TextView name;
        ImageView img;
        Button btn;
        BreaksListViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textData);
            img = (ImageView) itemView.findViewById(R.id.photo_data);
            /*name = (TextView) itemView.findViewById(R.id.name);
            age = (TextView) itemView.findViewById(R.id.age);
            phone = (TextView) itemView.findViewById(R.id.phone);
            email = (TextView) itemView.findViewById(R.id.email);
            provincia = (TextView) itemView.findViewById(R.id.provincia);
            btn = (Button) itemView.findViewById(R.id.row_btn);*/
        }
    }

}
