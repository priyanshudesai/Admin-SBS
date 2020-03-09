package com.appsnipp.admin.maintence_recycle;

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
import com.appsnipp.admin.accout_fragment.maintenance_status;
import com.appsnipp.admin.apiinterface.responce_get_set.mainte_get_set;

import java.util.List;

public class maintence_adapter extends RecyclerView.Adapter<maintence_adapter.ViewHolder> {
    Context mcontext;
    private List<mainte_get_set> maintence_data;
    public maintence_adapter(List<mainte_get_set> maintence_data,Context mcontext){
        this.maintence_data=maintence_data;
        this.mcontext=mcontext;
    }


    @NonNull
    @Override
    public maintence_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.maintence_layout,null);
        ViewHolder viewHolder=new ViewHolder(itemview);

        viewHolder.stsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mainteid=maintence_data.get(viewHolder.getAdapterPosition()).getId()+"";
                mcontext.startActivity(new Intent(mcontext, maintenance_status.class)
                        .putExtra("mainteid",mainteid));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull maintence_adapter.ViewHolder viewHolder, int i) {
       mainte_get_set d=maintence_data.get(i);

        viewHolder.ma_name.setText(d.getBillname());
        viewHolder.ma_ammount.setText(d.getAmount());
        viewHolder.ma_last_date.setText(d.getLastdate());

    }

    @Override
    public int getItemCount() {
        return maintence_data.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView ma_name,ma_ammount,ma_last_date;
        public Button stsl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ma_name=(TextView) itemView.findViewById(R.id.maintence_name2);
            ma_ammount=(TextView) itemView.findViewById(R.id.maintence_ammount2);
            ma_last_date=(TextView) itemView.findViewById(R.id.maintence_last_date2);
            stsl=itemView.findViewById(R.id.maintence_sts);

        }
    }
}
