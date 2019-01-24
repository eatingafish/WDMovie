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

public class SoonAdapter_Rv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    /*private ArrayList<>*/
    private Context mContext;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_soon, viewGroup, false);

        return new Vh_Soon(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class Vh_Soon extends RecyclerView.ViewHolder {

        private final SimpleDraweeView mSdv_Soon;
        private final TextView mTv_Soon;

        public Vh_Soon(@NonNull View itemView) {
            super(itemView);

            mSdv_Soon = itemView.findViewById(R.id.mSdv_Soon);
            mTv_Soon = itemView.findViewById(R.id.mTv_Soon);

        }
    }

}
