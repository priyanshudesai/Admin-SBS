package com.appsnipp.admin.bill_recycle;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.admin.R;
import com.appsnipp.admin.apiinterface.Api;
import com.appsnipp.admin.apiinterface.ApiClient;
import com.appsnipp.admin.apiinterface.CommanResponse;
import com.appsnipp.admin.apiinterface.responce.bill_child_responce;
import com.appsnipp.admin.apiinterface.responce.bill_responce;
import com.appsnipp.admin.apiinterface.responce.spnt_total_responce;
import com.appsnipp.admin.apiinterface.responce_get_set.bill_child_get_set;
import com.appsnipp.admin.apiinterface.responce_get_set.bill_get_set;
import com.appsnipp.admin.apiinterface.responce_get_set.notice_get_set;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class bill_adapter extends RecyclerView.Adapter<bill_adapter.ViewHolder>{
    List<bill_child_get_set> li;

   List<bill_get_set> bill_data;
    Dialog mydialog;
    Context context;

    public bill_adapter(List<bill_get_set> bill_data, Context context) {
        this.bill_data = bill_data;
        this.context = context;
    }

    @NonNull
    @Override
    public bill_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bill_layout,null);

        ViewHolder viewHolder=new ViewHolder(itemview);
        mydialog =new Dialog(context);
        mydialog.setContentView(R.layout.bill_spent_popup);


        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                    EditText spnt_name=(EditText) mydialog.findViewById(R.id.spnt_name);
                EditText spnt_amt=(EditText) mydialog.findViewById(R.id.spnt_amt);

                String id=""+bill_data.get(viewHolder.getAdapterPosition()).getId();
                mydialog.findViewById(R.id.spnt_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sname=spnt_name.getText().toString();
                        String samt=spnt_amt.getText().toString();
                        Api api= ApiClient.getClient().create(Api.class);
                        Call<CommanResponse> call= api.accbillspntinsert("billspntinsert",id,sname,samt);
                            call.enqueue(new Callback<CommanResponse>() {
                                @Override
                                public void onResponse(Call<CommanResponse> call, Response<CommanResponse> response) {
                                    if (response.body().getSuccess()==200) {
                                        Toast.makeText(context, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                                        mydialog.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(context, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CommanResponse> call, Throwable t) {
                                    Toast.makeText(context, t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                });

                mydialog.show();
                return false;
            }
        });
//        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent motionEvent) {
//                if (mydialog.isShowing()) {
//                    v.getParent().requestDisallowInterceptTouchEvent(true);
//                    int action = motionEvent.getActionMasked();
//                    if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
//                        v.getParent().requestDisallowInterceptTouchEvent(false);
//                        mydialog.dismiss();
//                        return true;
//                    }
//                }
//                return false;
//
//            }
//        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull bill_adapter.ViewHolder viewHolder, int i) {

        bill_get_set d=bill_data.get(i);

        viewHolder.bi_no.setText(""+d.getId());
        viewHolder.bi_name.setText(d.getBillname());
        viewHolder.bi_total.setText(d.getTotalamt());
        //viewHolder.bi_due.setText();
        viewHolder.bi_date.setText(d.getBilldate());

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        viewHolder.childRV.setLayoutManager(layoutManager);
        viewHolder.childRV.setHasFixedSize(true);


        String r=""+bill_data.get(i).getId();
        String r2=""+bill_data.get(i).getTotalamt();
        loadspent(r,viewHolder);
        spntdue(r,r2,viewHolder);




    }

    private void spntdue(String r, String r2,ViewHolder viewHolder) {
        Api api= ApiClient.getClient().create(Api.class);
        Call<spnt_total_responce> call=api.spnttotal("spenttotal",r);
        call.enqueue(new Callback<spnt_total_responce>() {
            @Override
            public void onResponse(Call<spnt_total_responce> call, Response<spnt_total_responce> response) {
                if (response.body().getSuccess()==200) {
                    String o = response.body().getDe().getSum();
                    if(o!=null) {
                        int pp = Integer.parseInt(o);
                        int i2 = Integer.parseInt(r2);
                        int re = i2 - pp;
                        viewHolder.bi_due.setText("" + re);
                    }
                    else {
                        viewHolder.bi_due.setText(r2);
                    }

                }
            }

            @Override
            public void onFailure(Call<spnt_total_responce> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return bill_data.size();
//        return 2;
    }




    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public RecyclerView childRV;
        public TextView bi_no,bi_name,bi_total,bi_due,bi_date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bi_no=(TextView) itemView.findViewById(R.id.bill_text2);
            bi_name=(TextView) itemView.findViewById(R.id.bill_name2);
            bi_total=(TextView) itemView.findViewById(R.id.bill_total);
            bi_due=(TextView) itemView.findViewById(R.id.bill_due);
            bi_date=(TextView) itemView.findViewById(R.id.bill_date2);
            childRV=(RecyclerView) itemView.findViewById(R.id.bill_child_recycle);


        }
    }


    public void loadspent(String s,ViewHolder viewHolder)
    {
        Api api= ApiClient.getClient().create(Api.class);
        Call<bill_child_responce> call=api.billchild("billspntdetails",s);
        call.enqueue(new Callback<bill_child_responce>() {
            @Override
            public void onResponse(Call<bill_child_responce> call, Response<bill_child_responce> response) {
                if (response.body().getSuccess()==200) {

                    li=response.body().getDe();
                    Collections.reverse(li);
                    bill_child_adapter ada=new bill_child_adapter(li,context);

                    viewHolder.childRV.setAdapter(ada);
                    ada.notifyDataSetChanged();
//                    LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_anmimation_fall_down);
//                    recyclerView.setLayoutAnimation(layoutAnimationController);
                }
                else {
                    Toast.makeText(context, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onFailure(Call<bill_child_responce> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

