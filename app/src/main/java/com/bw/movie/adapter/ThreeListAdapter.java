package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.MovieMessageActivity;
import com.bw.movie.bean.MovieBean;
import com.bw.movie.bean.MovieListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @author happy_movie
 * @date 2019/1/23 19:42
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
   public class ThreeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    MyLove myLove;

    public ThreeListAdapter(Context context, List<MovieBean> datas){
        this.mContext=context;
        this.mDatas=datas;
        inflater=LayoutInflater.from(mContext);
    }

    public   void xihuan(MyLove myLove){
        this.myLove=myLove;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件


    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.threelist_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final int i) {

        if (viewHolder instanceof MyViewHolder){
            ((MyViewHolder) viewHolder).simpleDraweeView.setImageURI(Uri.parse(mDatas.get(i).getImageUrl()));
            ((MyViewHolder) viewHolder).name.setText(mDatas.get(i).getName());
            ((MyViewHolder) viewHolder).cont.setText(mDatas.get(i).getSummary());
            int followMovie = mDatas.get(i).isFollowMovie();
            ((MyViewHolder) viewHolder).love.setChecked(followMovie==1? true:false);
            ((MyViewHolder) viewHolder).love.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) v;
                    boolean checked = checkBox.isChecked();
                    if(checked){
                        myLove.onLove(i);
                    }else {
                        myLove.onCancle(i);
                    }
                }
            });
            ((MyViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(mContext, MovieMessageActivity.class);
                int id = mDatas.get(i).getId();
                intent.putExtra("movieid",id);
                mContext.startActivity(intent);
                }
            });
        }


    }

    class MyViewHolder extends RecyclerView.ViewHolder { //承载Item视图的子布局
        SimpleDraweeView simpleDraweeView;
        TextView name;
        TextView cont;
        CheckBox love;

        public MyViewHolder(View view) {
            super(view);
            simpleDraweeView = view.findViewById(R.id.iv);
            name = view.findViewById(R.id.name);
            cont = view.findViewById(R.id.cont);
            love = view.findViewById(R.id.love);
        }
    }

    public interface MyLove{
        void onLove(int possion);
        void onCancle(int possion);
    }


}