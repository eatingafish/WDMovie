package com.bw.movie.adapter.frag2adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.cinema.CinemazuoweiActivity;
import com.bw.movie.bean.cinema.Cinemayingp;

import java.util.ArrayList;
import java.util.List;


public class CinemaPaiqiAdapter extends RecyclerView.Adapter<CinemaPaiqiAdapter.VH> {

    private Context context;

    public CinemaPaiqiAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Cinemayingp> list = new ArrayList<>();

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(context, R.layout.cinemapay_list, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.ting.setText(list.get(i).getScreeningHall());
        vh.starttime.setText(list.get(i).getBeginTime());
        vh.endtime.setText(list.get(i).getEndTime());
        vh.money.setText(list.get(i).getPrice()+"");
        vh.money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,CinemazuoweiActivity.class));
            }
        });
        vh.xuanzuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,CinemazuoweiActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<Cinemayingp> result) {
        if (result != null)
        {
            list.addAll(result);
        }
    }

    public void remove() {
        list.clear();
    }

    public class VH extends RecyclerView.ViewHolder {

        private final TextView ting;
        private final TextView starttime;
        private final TextView endtime;
        private final TextView money;
        private final ImageView xuanzuo;

        public VH(@NonNull View itemView) {
            super(itemView);
            ting = itemView.findViewById(R.id.pay_ting);
            starttime = itemView.findViewById(R.id.pay_starttime);
            endtime = itemView.findViewById(R.id.pay_endtime);
            money = itemView.findViewById(R.id.pay_money);
            xuanzuo = itemView.findViewById(R.id.xuanzuo);
        }
    }
}
