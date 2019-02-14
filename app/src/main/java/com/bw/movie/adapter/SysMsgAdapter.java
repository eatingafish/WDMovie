package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.FindAllSysMsgList;

import java.util.ArrayList;
import java.util.List;

public class SysMsgAdapter extends RecyclerView.Adapter {

    private Context context;

    public SysMsgAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<FindAllSysMsgList> list = new ArrayList<>();

    public void addItem(List<FindAllSysMsgList> sysMsgListBeans) {
        if(sysMsgListBeans!=null)
        {
            list.addAll(sysMsgListBeans);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_sysmsg, null);
        SysMsgVH sysMsgVH = new SysMsgVH(view);
        return sysMsgVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SysMsgVH sysMsgVH = (SysMsgVH) viewHolder;
        FindAllSysMsgList findAllSysMsgList = list.get(i);
        sysMsgVH.sysmsg_name.setText(findAllSysMsgList.getTitle());
        sysMsgVH.sysmsg_msg.setText(findAllSysMsgList.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    //创建VH
    class SysMsgVH extends RecyclerView.ViewHolder {

        public TextView sysmsg_name;
        public TextView sysmsg_msg;
        public SysMsgVH(@NonNull View itemView) {
            super(itemView);
            sysmsg_name = itemView.findViewById(R.id.sysmsg_name);
            sysmsg_msg = itemView.findViewById(R.id.sysmsg_msg);
        }
    }
}
