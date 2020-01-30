package com.appsnipp.admin.resource_list;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsnipp.admin.R;
import com.appsnipp.admin.apiinterface.responce_get_set.resource_get_set;

import java.util.List;

public class resource_adapter extends RecyclerView.Adapter<resource_adapter.ViewHolder> {

    Context mcontext;
    private List<resource_get_set> data;
    public resource_adapter(Context mcontext,List<resource_get_set> data){
        this.data=data;
        this.mcontext=mcontext;
    }


    @NonNull
    @Override
    public resource_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resource_list,null);

        ViewHolder viewHolder=new ViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull resource_adapter.ViewHolder viewHolder, int i) {
        resource_get_set d=data.get(i);

        viewHolder.re_name.setText(d.getName());
        viewHolder.flat_no.setText(d.getCapacity());
        viewHolder.re_name_.setText(d.getCharge());
        viewHolder.re_detail.setText(d.getDetails());
       // viewHolder.re_img.setImageResource(d.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView re_name,flat_no,re_name_,re_detail;
        ImageView re_img ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            re_name=(TextView) itemView.findViewById(R.id.res_name);
            flat_no=(TextView) itemView.findViewById(R.id.flat_no);
            re_name_=(TextView) itemView.findViewById(R.id.name);
            re_detail=(TextView) itemView.findViewById(R.id.details);
            re_img=(ImageView) itemView.findViewById(R.id.res_img);


        }
    }
}
