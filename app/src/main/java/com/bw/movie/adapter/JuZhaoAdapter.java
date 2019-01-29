package com.bw.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;

import java.util.List;


public class JuZhaoAdapter extends RecyclerView.Adapter<JuZhaoAdapter.MyViewHolder>  {
    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public JuZhaoAdapter(Context context, List<String> datas){
        this.mContext=context;
        this.mDatas=datas;
        inflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(mContext).load(mDatas.get(position)).into(holder.simpleDraweeView);
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.juzhao_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    public void addData(List<String> posterList) {
    }

    class MyViewHolder extends RecyclerView.ViewHolder { //承载Item视图的子布局
        ImageView simpleDraweeView;

        public MyViewHolder(View view) {
            super(view);
            simpleDraweeView = view.findViewById(R.id.iv3);
        }
    }


}