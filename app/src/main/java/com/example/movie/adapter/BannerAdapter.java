package com.example.movie.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import recycler.coverflow.RecyclerCoverFlow;

public class BannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int[] mImages = {R.drawable.gegelaoshi,
            R.drawable.baobei, R.drawable.hutaojiazi, R.drawable.lichadeguma,
            R.drawable.menchongguojiang, R.drawable.muguangbali,
            R.drawable.tiexuezhanshi, R.drawable.weinixieshi,
            R.drawable.wdejiandie, R.drawable.wushuang, R.drawable.xueguai,
            R.drawable.ying, R.drawable.zhaodaoni, R.drawable.zuoriqingkong
    };

    public BannerAdapter(Context mActivity) {
        this.mActivity = mActivity;
    }

    private Context mActivity;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.item_banner, viewGroup, false);


        return new Vh_Banner(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        if (viewHolder instanceof Vh_Banner){

            Glide.with(mActivity).load(mImages[i%mImages.length]).into(((Vh_Banner) viewHolder).mSdv);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null){
                        onItemClick.clickItem(i);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mImages.length;
    }

    class Vh_Banner extends RecyclerView.ViewHolder {

        private final ImageView mSdv;

        public Vh_Banner(@NonNull View itemView) {
            super(itemView);
            mSdv = itemView.findViewById(R.id.mSdv);

        }
    }

    public void setOnItemClick(BannerAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private onItemClick onItemClick;
    interface  onItemClick{
        void clickItem(int position);
    }
}
