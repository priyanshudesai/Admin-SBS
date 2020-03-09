package com.appsnipp.admin.accout_fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;


import com.appsnipp.admin.R;
import com.appsnipp.admin.apiinterface.Api;
import com.appsnipp.admin.apiinterface.ApiClient;
import com.appsnipp.admin.apiinterface.responce.bill_responce;
import com.appsnipp.admin.apiinterface.responce.notice_responce;
import com.appsnipp.admin.apiinterface.responce_get_set.bill_get_set;
import com.appsnipp.admin.bill_recycle.bill_adapter;
import com.appsnipp.admin.bill_recycle.bill_data;
import com.appsnipp.admin.notice_recycle.notice_adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class bill_details extends Fragment {
    RecyclerView recyclerView;
    List<bill_get_set> li;
    bill_adapter ada;
    SwipeRefreshLayout swipe;
    public bill_details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill_details, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.bill_recycle);
//        li = new ArrayList<>();
//        bill_data data[] = {new bill_data("000001","diwali","12000","1200")
//                ,new bill_data("000002","holi","12345","234")
//                ,new bill_data( "000003","society paint","2345356","00")
//                ,new bill_data("000004","diwali","12000","1200")
//                ,new bill_data("000005","holi","12345","234")
//                ,new bill_data( "000006","society paint","2345356","00")};
//        for(int i=0;i< data.length;i++){
//            li.add(data[i]);
//        }

        swipe=(SwipeRefreshLayout) view.findViewById(R.id.swipe_bill);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.lite_blue));

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        loadbill();
                        swipe.setRefreshing(false);

                    }
                }, 2000);
                //swipe.setRefreshing(false);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadbill();


        return view;
    }

    public void loadbill()
    {
        Api api= ApiClient.getClient().create(Api.class);
        Call<bill_responce> call=api.accbilldetails("billdetails");
        call.enqueue(new Callback<bill_responce>() {
            @Override
            public void onResponse(Call<bill_responce> call, Response<bill_responce> response) {
                if (response.body().getSuccess()==200) {

                    li=response.body().getDe();
                    Collections.reverse(li);
                    ada=new bill_adapter(li,getContext());
                    recyclerView.setAdapter(ada);
                    LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_anmimation_fall_down);
                    recyclerView.setLayoutAnimation(layoutAnimationController);
                }
                else {
                 //   Toast.makeText(getContext(), response.body().getMessage()+"", Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onFailure(Call<bill_responce> call, Throwable t) {
             //   Toast.makeText(getContext(), t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
