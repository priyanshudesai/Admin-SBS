package com.appsnipp.admin.accout_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appsnipp.admin.R;
import com.appsnipp.admin.apiinterface.responce_get_set.mainte_get_set;
import com.appsnipp.admin.apiinterface.responce_get_set.maintre_get_set;
import com.appsnipp.admin.maintence_recycle.maintence_adapter;

import java.util.List;

public class main_status_adapter extends RecyclerView.Adapter<main_status_adapter.ViewHolder>{
        Context mcontext;
private List<maintre_get_set> maintence_data;
public main_status_adapter(List<maintre_get_set> maintence_data,Context mcontext){
        this.maintence_data=maintence_data;
        this.mcontext=mcontext;
        }


@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i){
        View itemview=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mainte_status_layout,null);
        ViewHolder viewHolder=new ViewHolder(itemview);

        return viewHolder;
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder viewHolder,int i){
        maintre_get_set d=maintence_data.get(i);

        viewHolder.ma_name.setText(d.getBillname());
        viewHolder.ma_flatno.setText(d.getFlatno());
        }

@Override
public int getItemCount(){
        return maintence_data.size();
        }

public static class ViewHolder extends RecyclerView.ViewHolder {

    public TextView ma_name, ma_flatno;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        ma_name = (TextView) itemView.findViewById(R.id.mainsts_name);
        ma_flatno = (TextView) itemView.findViewById(R.id.mainsts_flatno);
    }
}
}
