package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;

public class BannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int[] mImages = {R.drawable.gegelaoshi,
            R.drawable.baobei, R.drawable.hutaojiazi, R.drawable.lichadeguma,
            R.drawable.menchongguojiang, R.drawable.muguangbali,
            R.drawable.tiexuezhanshi, R.drawable.weinixieshi,
            R.drawable.wdejiandie, R.drawable.wushuang, R.drawable.xueguai,
            R.drawable.ying, R.drawable.zhaodaoni, R.drawable.zuoriqingkong
    };
    private String[] name = {"宝贝儿   101分钟", "嗝嗝老师   121分钟", "胡桃夹子和四个王国   108分钟"
            , "李茶的姑妈   128分钟", "猛虫过江   114分钟", "暮光巴黎   135分钟", "铁血战士   140分钟", "为你写诗   97分钟"
            , "我的间谍前男友   101分钟", "无双   101分钟", "雪怪大冒险   101分钟", "影   101分钟"
            , "找到你   95分钟", "昨日青空   89分钟"
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

           // Glide.with(mActivity).load(mImages[i%mImages.length]).into(((Vh_Banner) viewHolder).mSdv);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null){
                        onItemClick.clickItem(i);
                    }

                }
            });
            ((Vh_Banner) viewHolder).textView.setBackgroundColor(0x55000000);
            ((Vh_Banner) viewHolder).textView.setText(name[i % name.length]);
        }

    }

    @Override
    public int getItemCount() {
        return mImages.length;
    }

    class Vh_Banner extends RecyclerView.ViewHolder {

        private final ImageView mSdv;
        private final TextView textView;

        public Vh_Banner(@NonNull View itemView) {
            super(itemView);
            mSdv = itemView.findViewById(R.id.mSdv);
            textView = itemView.findViewById(R.id.tv);

        }
    }

    public void setOnItemClick(BannerAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public onItemClick onItemClick;
    public interface  onItemClick{
        void clickItem(int position);
    }
}
