package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.Moviecinema;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MoviebuyAdapter extends RecyclerView.Adapter<MoviebuyAdapter.VH> {

    private Context context;

    public MoviebuyAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Moviecinema> list = new ArrayList<>();


    //接口回调
    public interface OnListCinemaLister{
        void onList(int id,String name,String address);
    }

    public OnListCinemaLister onListCinemaLister;

    public void setOnListCinemaLister(OnListCinemaLister onListCinemaLister) {
        this.onListCinemaLister = onListCinemaLister;
    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(context, R.layout.moviebuy_item, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, final int i) {
        vh.name.setText(list.get(i).getName());
        vh.address.setText(list.get(i).getAddress());
        vh.distance.setText(list.get(i).getDistance()+"km");
        vh.simple.setImageURI(Uri.parse(list.get(i).getLogo()));
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cinemaid = list.get(i).getId();
                String cinemaname = list.get(i).getName();
                String address = list.get(i).getAddress();
                if (onListCinemaLister != null)
                {
                    onListCinemaLister.onList(cinemaid,cinemaname,address);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<Moviecinema> result) {
        if (result != null)
        {
            list.addAll(result);
        }
    }

    public class VH extends RecyclerView.ViewHolder {

        private final TextView name;
        private final SimpleDraweeView simple;
        private final TextView address;
        private final TextView distance;
        private final ImageView xin;

        public VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.buy_name);
            simple = itemView.findViewById(R.id.buy_simple);
            address = itemView.findViewById(R.id.buy_aaddress);
            distance = itemView.findViewById(R.id.buy_distance);
            xin = itemView.findViewById(R.id.buy_xin);
        }
    }


}
