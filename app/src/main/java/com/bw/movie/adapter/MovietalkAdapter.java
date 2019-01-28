package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.Movietalkbean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovietalkAdapter extends XRecyclerView.Adapter<MovietalkAdapter.VH> {

    private Context context;

    public MovietalkAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Movietalkbean> list = new ArrayList<>();


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(context, R.layout.movietalk_list, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.touxiang.setImageURI(Uri.parse(list.get(i).getCommentHeadPic()));
        vh.name.setText(list.get(i).getCommentUserName());
        vh.number.setText(list.get(i).getReplyNum()+"");
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        Date date = new Date(list.get(i).getCommentTime());
        String time = format.format(date);
        vh.time.setText(time);
        vh.content.setText(list.get(i).getCommentContent());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<Movietalkbean> result) {
        if (result != null)
        {
            list.addAll(result);
        }
    }

    public void remove() {
        list.clear();
    }

    public class VH extends RecyclerView.ViewHolder {

        private final SimpleDraweeView touxiang;
        private final TextView name;
        private final TextView content;
        private final TextView time;
        private final CheckBox zan;
        private final TextView number;

        public VH(@NonNull View itemView) {
            super(itemView);
            touxiang = itemView.findViewById(R.id.talk_touxiang);
            name = itemView.findViewById(R.id.talk_name);
            content = itemView.findViewById(R.id.talk_content);
            time = itemView.findViewById(R.id.talk_time);
            zan = itemView.findViewById(R.id.talk_zan);
            number = itemView.findViewById(R.id.talk_number);
        }
    }
}
