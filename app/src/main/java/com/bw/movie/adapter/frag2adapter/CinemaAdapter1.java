package com.bw.movie.adapter.frag2adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.cinema.PaylistActivity;
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
    public onItemClick onItemClick;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LinearLayout.inflate(context, R.layout.crinema_recycler, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH vh, final int i) {
        vh.simple.setImageURI(list.get(i).getLogo());
        vh.name.setText(list.get(i).getName());
        vh.address.setText(list.get(i).getAddress());
        vh.distance.setText(list.get(i).getDistance() + "km");
        Log.e("TAG---", "onBindViewHolder: "+list.get(i).getFollowCinema()+list.get(i).getName() );
        if (list.get(i).getFollowCinema()==1){
            vh.xin.setBackgroundResource(R.drawable.xin2);
        }else {
            vh.xin.setBackgroundResource(R.drawable.xin3);
        }
        vh.xin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onItemClick != null){
                    onItemClick.onClick(isChecked, list.get(i).getId(),vh.xin,list.get(i).getFollowCinema());
                }


            }
        });

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaylistActivity.class);
                intent.putExtra("id", list.get(i).getId());
                intent.putExtra("logo", list.get(i).getLogo());
                intent.putExtra("name", list.get(i).getName());
                Toast.makeText(context, "" + list.get(i).getName(), Toast.LENGTH_SHORT).show();
                intent.putExtra("address", list.get(i).getAddress());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<Cinemabean> result) {
        list.clear();
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
        private final CheckBox xin;
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


    public void setOnItemClick(CinemaAdapter1.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface onItemClick {
        void onClick(boolean isChecked, int id,CheckBox xin,int isc);
    }

}
