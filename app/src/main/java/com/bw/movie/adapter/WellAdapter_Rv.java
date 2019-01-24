package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class WellAdapter_Rv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    /*private ArrayList<>*/
    private Context mContext;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_populer, viewGroup, false);

        return new Vh_Well(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
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

}
