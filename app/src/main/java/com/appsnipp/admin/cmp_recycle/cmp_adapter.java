package com.appsnipp.admin.cmp_recycle;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.admin.R;
import com.appsnipp.admin.apiinterface.Api;
import com.appsnipp.admin.apiinterface.ApiClient;
import com.appsnipp.admin.apiinterface.CommanResponse;
import com.appsnipp.admin.apiinterface.responce.cmp_responce;
import com.appsnipp.admin.apiinterface.responce_get_set.cmp_get_set;
import com.appsnipp.admin.apiinterface.responce_get_set.event_get_set;
import com.appsnipp.admin.event_recycle_view.event_adapter;
import com.appsnipp.admin.notice_recycle.notice_adapter;
import com.appsnipp.admin.notice_recycle.notice_data;
import com.appsnipp.admin.resource_list.resource_adapter;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class cmp_adapter extends RecyclerView.Adapter<cmp_adapter.ViewHolder> {
    Context mcontext;
    Dialog mydialog;
    AlertDialog.Builder builder;
    private List<cmp_get_set> cmp_data;

    public cmp_adapter(Context mcontext, List<cmp_get_set> cmp_data) {
        this.cmp_data = cmp_data;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complain_layout, null);
        ViewHolder viewHolder = new ViewHolder(itemview);
        mydialog = new Dialog(mcontext);
        mydialog.setContentView(R.layout.popup_layout_complain);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView title = (TextView) mydialog.findViewById(R.id.cm_title);
                TextView description = (TextView) mydialog.findViewById(R.id.cm_descr);
                TextView flat = (TextView) mydialog.findViewById(R.id.cm_flat);
                ImageView img = (ImageView) mydialog.findViewById(R.id.cm_img);
                Button s = mydialog.findViewById(R.id.cm_save);
                Button svd = mydialog.findViewById(R.id.cm_svd);
                title.setText(cmp_data.get(viewHolder.getAdapterPosition()).getCtitle());
                description.setText(cmp_data.get(viewHolder.getAdapterPosition()).getDiscription());
                flat.setText(cmp_data.get(viewHolder.getAdapterPosition()).getFlatno());
                Glide.with(mcontext)
                        .load(cmp_data.get(viewHolder.getAdapterPosition()).getDocument_file())
                        .into(img);
                s.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater inflater1 = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v = inflater1.inflate(R.layout.popup_solvecmp, null);


                        TextView t = v.findViewById(R.id.mno);
                        TextView t1 = v.findViewById(R.id.mno1);
                        builder = new AlertDialog.Builder(mcontext);

                        builder.setView(v);

                        t.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String number = "+917383846827";
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + number));
                                if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                mcontext.startActivity(intent);
                            }
                        });

                        t1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String number = "+917383846827";
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + number));
                                if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                mcontext.startActivity(intent);
                            }
                        });



                        builder.setCancelable(true);
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                });
                svd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Api api= ApiClient.getClient().create(Api.class);
                        Call<CommanResponse> call= api.cmp_status("complaincall",
                                cmp_data.get(viewHolder.getAdapterPosition()).getId()+"",
                                "Solved");
                        call.enqueue(new Callback<CommanResponse>() {
                            @Override
                            public void onResponse(Call<CommanResponse> call, Response<CommanResponse> response) {
                                Toast.makeText(mcontext, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                                mydialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<CommanResponse> call, Throwable t) {
                                Toast.makeText(mcontext, t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                mydialog.show();
            }
        });
       // ViewHolder viewHolder=new ViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        cmp_get_set d=cmp_data.get(i);

        viewHolder.cp_title.setText(d.getCtitle());
        viewHolder.cp_des.setText(d.getDiscription());
        viewHolder.cp_flat.setText(d.getFlatno());
      //  viewHolder.cp_img.setText(d.getDate());
        Glide.with(mcontext)
                .load(d.getDocument_file())
                .into(viewHolder.cp_img);

    }

    @Override
    public int getItemCount() {
        return cmp_data.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView cp_title,cp_des,cp_flat;
        public ImageView cp_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cp_title=(TextView) itemView.findViewById(R.id.cmpr_ttl);
            cp_des=(TextView) itemView.findViewById(R.id.cmpr_desc);
            cp_flat=(TextView) itemView.findViewById(R.id.cmpr_flatno);
            cp_img=(ImageView) itemView.findViewById(R.id.cmpr_img);


        }
    }

}
