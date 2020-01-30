package com.appsnipp.admin.Navigation_Profile.ui.resource;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appsnipp.admin.R;
import com.appsnipp.admin.apiinterface.Api;
import com.appsnipp.admin.apiinterface.ApiClient;
import com.appsnipp.admin.apiinterface.CommanResponse;
import com.appsnipp.admin.apiinterface.responce.event_responce;
import com.appsnipp.admin.apiinterface.responce.resource_responce;
import com.appsnipp.admin.apiinterface.responce_get_set.resource_get_set;
import com.appsnipp.admin.resource_list.resource_adapter;
import com.appsnipp.admin.resource_list.data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResourceFragment extends Fragment {

    RecyclerView recyclerView;
    resource_adapter ev;
    List<resource_get_set> li;
    FloatingActionButton f;
    AlertDialog.Builder builder;
    EditText resf_name,resf_detail,resf_capacity,resf_charge;
    Button Sv;
    String resfs_name,resfs_detail,resfs_capacity,resfs_charge;
    private ResourceViewModel resourceViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resourceViewModel =
                ViewModelProviders.of(this).get(ResourceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_resources, container, false);
        //final TextView textView = root.findViewById(R.id.text_resource);
        resourceViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //     textView.setText(s);
            }
        });
        recyclerView=(RecyclerView) root.findViewById(R.id.list_res);
//        li=new ArrayList<>();
//        data azad[]={new data("b-101","club","kausahl","kausahl jethava",R.drawable.club),new data("b-102","club","kaushik","kausahik mokariya",R.drawable.club)
//                ,new data("b-102","club","priyanshu","priyanshu desai",R.drawable.club)
//                ,new data("b-102","club","aditya","aditya panchal",R.drawable.club)
//                ,new data("b-102","club","shakshi","shakshi patel",R.drawable.club)
//                ,new data("b-102","club","danika","danika bhatt",R.drawable.club)
//                ,new data("b-102","club","kuldeep","kuldeep jethava",R.drawable.club)};
//
//        for(int i=0;i<azad.length;i++)
//
//        {
//            li.add(azad[i]);
//
//        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Api api= ApiClient.getClient().create(Api.class);
        Call<resource_responce> call= api.resourcedetail("resourcedetail");
        call.enqueue(new Callback<resource_responce>() {
            @Override
            public void onResponse(Call<resource_responce> call, Response<resource_responce> response) {
                li=response.body().getDe();
                ev=new resource_adapter(getContext(),li);
                recyclerView.setAdapter(ev);
            }

            @Override
            public void onFailure(Call<resource_responce> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

f=(FloatingActionButton) root.findViewById(R.id.fab_res);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder= new AlertDialog.Builder(getContext());
                LayoutInflater inflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v=inflater.inflate(R.layout.resource_form,null);
                builder.setView(v);
                builder.setCancelable(true);
                AlertDialog alert=builder.create();


                resf_name=(EditText) v.findViewById(R.id.ref_name);
                resf_capacity=(EditText) v.findViewById(R.id.ref_capacity);
                resf_charge=(EditText) v.findViewById(R.id.ref_Charge);
                resf_detail=(EditText) v.findViewById(R.id.ref_detail);
                Sv=(Button) v.findViewById(R.id.ref_save);
                Sv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resfs_name=resf_name.getText().toString().trim();
                        resfs_capacity=resf_capacity.getText().toString().trim();
                        resfs_charge=resf_charge.getText().toString().trim();
                        resfs_detail=resf_detail.getText().toString().trim();


                        Api api = ApiClient.getClient().create(Api.class);
                        Call<CommanResponse> call=api.resourceentry("resourceentry",resfs_name,resfs_capacity,resfs_charge,resfs_detail);

                        call.enqueue(new Callback<CommanResponse>() {
                            @Override
                            public void onResponse(Call<CommanResponse> call, Response<CommanResponse> response) {
                                if (response.body().getSuccess()==200) {
                                    Toast.makeText(getContext(), response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CommanResponse> call, Throwable t) {
                                Toast.makeText(getContext(), t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
                            }
                        });


                        alert.dismiss();
                    }
                });
                alert.show();
            }
        });

        return root;
    }
}