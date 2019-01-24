package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.MovieBean;
import com.bw.movie.frag.Fragmain1;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   /* private int[] mImages = {R.drawable.gegelaoshi,
            R.drawable.baobei, R.drawable.hutaojiazi, R.drawable.lichadeguma,
            R.drawable.menchongguojiang, R.drawable.muguangbali,
            R.drawable.tiexuezhanshi, R.drawable.weinixieshi,
            R.drawable.wdejiandie, R.drawable.wushuang, R.drawable.xueguai,
            R.drawable.ying, R.drawable.zhaodaoni, R.drawable.zuoriqingkong
    };
    private String[] name = {"嗝嗝老师   121分钟","宝贝儿   101分钟", "胡桃夹子和四个王国   108分钟"
            , "李茶的姑妈   128分钟", "猛虫过江   114分钟", "暮光巴黎   135分钟", "铁血战士   140分钟", "为你写诗   97分钟"
            , "我的间谍前男友   101分钟", "无双   101分钟", "雪怪大冒险   101分钟", "影   101分钟"
            , "找到你   95分钟", "昨日青空   89分钟"
    };*/

   private ArrayList<MovieBean> list = new ArrayList<>();
    private Context mActivity;
    private onItemClick clickCb;

    public BannerAdapter(Context c) {
        mActivity = c;
    }

    public BannerAdapter(Context c, onItemClick cb) {
        mActivity = c;
        clickCb = cb;
    }

    public void setOnClickLstn(onItemClick cb) {
        this.clickCb = cb;
    }

    public BannerAdapter(BannerAdapter.onItemClick onItemClick, Context mActivity) {
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
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickCb != null) {
                        clickCb.clickItem(i);
                    }

                }
            });
            ((Vh_Banner) viewHolder).textView.setBackgroundColor(0x55000000);
            ((Vh_Banner) viewHolder).textView.setText(list.get(i).getName()+"　"+list.get(i).getReleaseTimeShow());
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
}
