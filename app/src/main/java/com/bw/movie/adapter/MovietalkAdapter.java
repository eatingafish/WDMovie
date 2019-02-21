package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.Movietalkbean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovietalkAdapter extends XRecyclerView.Adapter<MovietalkAdapter.VH> {

    private Context context;
    private boolean check = false;
    private int zannum;

    public MovietalkAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Movietalkbean> list = new ArrayList<>();

    private Onclick onclick;

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }

    public interface Onclick {
        void onClick(ShineButton zan, int commentId, TextView zannum, int greatNum);
    }

    onClickListener onClickListener;

    public void setOnClickListener(MovietalkAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickListener {
        void onClickListener(int commentId);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(context, R.layout.movietalk_list, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH vh, final int i) {
        vh.touxiang.setImageURI(Uri.parse(list.get(i).getCommentHeadPic()));
        vh.name.setText(list.get(i).getCommentUserName());
        vh.number.setText(list.get(i).getReplyNum() + "");
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        Date date = new Date(list.get(i).getCommentTime());
        String time = format.format(date);
        vh.time.setText(time);
        vh.content.setText(list.get(i).getCommentContent());
        final Movietalkbean movietalkbean = list.get(i);
        if (movietalkbean.getIsGreat() == 1 || movietalkbean.isClick()) {
            if (movietalkbean.isClick()) {
                int greatNum = movietalkbean.getGreatNum();
                vh.zannum.setText(String.valueOf(++greatNum));
            }
            vh.zan.setChecked(true);
        } else {
            vh.zan.setChecked(false);
        }
        vh.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!vh.zan.isChecked()) {
                    Toast.makeText(context, "不能重复点赞", Toast.LENGTH_SHORT).show();
                    vh.zan.setChecked(true);
                    return;
                }
                onclick.onClick(vh.zan, list.get(i).getCommentId(), vh.zannum, list.get(i).getGreatNum());
                if (movietalkbean.getIsGreat() == 0) {
                    movietalkbean.setClick(true);
                } else {
                    movietalkbean.setClick(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<Movietalkbean> result) {
        if (result != null) {
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
        private final ShineButton zan;
        private final TextView number;
        private final TextView zannum;

        public VH(@NonNull View itemView) {
            super(itemView);
            touxiang = itemView.findViewById(R.id.talk_touxiang);
            name = itemView.findViewById(R.id.talk_name);
            content = itemView.findViewById(R.id.talk_content);
            time = itemView.findViewById(R.id.talk_time);
            zan = itemView.findViewById(R.id.talk_zan);
            number = itemView.findViewById(R.id.talk_number);
            zannum = itemView.findViewById(R.id.zannum);
        }
    }
}
