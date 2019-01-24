package com.bw.movie.adapter.frag2adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.cinema.Cinemabean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:影院adapter
 */
public class CinemaAdapter1 extends RecyclerView.Adapter<CinemaAdapter1.VH> {

    private Context context;

    public CinemaAdapter1(Context context) {
        this.context = context;
    }

    private ArrayList<Cinemabean> list = new ArrayList<>();


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(context, R.layout.crinema_recycler, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.simple.setImageURI(list.get(i).getLogo());
        vh.name.setText(list.get(i).getName());
        vh.address.setText(list.get(i).getAddress());
        vh.distance.setText(list.get(i).getDistance()+"km");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<Cinemabean> result) {
        if (result != null) {
            list.addAll(result);
        }

    }

    public void remove() {
        list.clear();
    }


    public class VH extends RecyclerView.ViewHolder {

        private final SimpleDraweeView simple;
        private final TextView name;
        private final TextView address;
        private final ImageView xin;
        private final TextView distance;

        public VH(@NonNull View itemView) {
            super(itemView);
            simple = itemView.findViewById(R.id.crinema_simple);
            name = itemView.findViewById(R.id.crinema_name);
            address = itemView.findViewById(R.id.crinema_address);
            xin = itemView.findViewById(R.id.crinema_xin);
            distance = itemView.findViewById(R.id.crinema_distance);
        }
    }
}
