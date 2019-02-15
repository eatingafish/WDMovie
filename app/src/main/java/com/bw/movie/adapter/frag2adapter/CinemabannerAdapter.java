package com.bw.movie.adapter.frag2adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.cinema.Cinemamovie;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class CinemabannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Cinemamovie> list = new ArrayList<>();
    private Context mActivity;
    private onItemClick clickCb;

    public CinemabannerAdapter(Context c) {
        mActivity = c;
    }

    public CinemabannerAdapter(Context c, onItemClick cb) {
        mActivity = c;
        clickCb = cb;
    }

    public void setOnClickLstn(onItemClick cb) {
        this.clickCb = cb;
    }

    public CinemabannerAdapter(CinemabannerAdapter.onItemClick onItemClick, Context mActivity) {
        this.clickCb = onItemClick;
        this.mActivity = mActivity;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.item_banner, viewGroup, false);

        return new Vh_Banner(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        if (viewHolder instanceof Vh_Banner) {
            ((Vh_Banner) viewHolder).mSdv.setImageURI(Uri.parse(list.get(i).getImageUrl()));
            checkedMovieId.checkedMovieId(list.get(i).getId());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickCb != null) {
                        clickCb.clickItem(i);
                        checkedMovieId.checkedMovieId(list.get(i).getId());
                    }

                }
            });
            ((Vh_Banner) viewHolder).textView.getBackground().mutate().setAlpha(100);
            ((Vh_Banner) viewHolder).textView.setText(list.get(i).getName() + "　" + list.get(i).getDuration());

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<Cinemamovie> movieList) {
        list.clear();
        if (movieList != null) {
            list.addAll(movieList);
        }
    }

    public void remove()
    {
        list.clear();
    }

    class Vh_Banner extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final SimpleDraweeView mSdv;

        public Vh_Banner(@NonNull View itemView) {
            super(itemView);
            mSdv = itemView.findViewById(R.id.mSdv);
            textView = itemView.findViewById(R.id.tv);
        }
    }

    public interface onItemClick {
        void clickItem(int position);
    }

    public void setCheckedMovieId(CinemabannerAdapter.checkedMovieId checkedMovieId) {
        this.checkedMovieId = checkedMovieId;
    }

    private checkedMovieId checkedMovieId;
    public interface checkedMovieId{
        void checkedMovieId(int id);
    }
}
