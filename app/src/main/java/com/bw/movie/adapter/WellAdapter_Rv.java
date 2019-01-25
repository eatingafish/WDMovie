package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.MovieBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class WellAdapter_Rv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<MovieBean> list = new ArrayList<>();

    public WellAdapter_Rv(Context mContext) {
        this.mContext = mContext;
    }

    private Context mContext;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_well, viewGroup, false);

        return new Vh_Well(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        if (viewHolder instanceof Vh_Well){
            Log.e("TAG", "onBindViewHolder: "+ list.get(i).getImageUrl());
            ((Vh_Well) viewHolder).mSdv_Well.setImageURI(Uri.parse(list.get(i).getImageUrl()));
            ((Vh_Well) viewHolder).mTv_Well.setText(list.get(i).getName());
            ((Vh_Well) viewHolder).mTv_Well.setBackgroundColor(0x55000000);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendId.sendId(list.get(i).getId());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void addData(List<MovieBean> result) {

        if (result!=null){
            list.addAll(result);
        }

    }

    class Vh_Well extends RecyclerView.ViewHolder {

        private final SimpleDraweeView mSdv_Well;
        private final TextView mTv_Well;

        public Vh_Well(@NonNull View itemView) {
            super(itemView);

            mSdv_Well = itemView.findViewById(R.id.mSdv_Well);
            mTv_Well = itemView.findViewById(R.id.mTv_Well);

        }
    }

    public void setSendId(WellAdapter_Rv.sendId sendId) {
        this.sendId = sendId;
    }

    private sendId sendId;
    public interface sendId{
        void sendId(int movieid);
    }
}
