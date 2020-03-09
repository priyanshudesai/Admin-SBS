package com.appsnipp.admin.accout_fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.appsnipp.admin.R;
import com.appsnipp.admin.apiinterface.Api;
import com.appsnipp.admin.apiinterface.ApiClient;
import com.appsnipp.admin.apiinterface.responce.mainte_responce;
import com.appsnipp.admin.apiinterface.responce.maintre_responce;
import com.appsnipp.admin.apiinterface.responce_get_set.mainte_get_set;
import com.appsnipp.admin.apiinterface.responce_get_set.maintre_get_set;
import com.appsnipp.admin.maintence_recycle.maintence_adapter;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class maintenance_status extends AppCompatActivity {

    RecyclerView recyclerView;
    List<maintre_get_set> li;
    main_status_adapter ada;
    SwipeRefreshLayout swipe;
    String me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintenance_status);

        recyclerView=(RecyclerView) findViewById(R.id.mstatus_recycle);
        swipe=(SwipeRefreshLayout) findViewById(R.id.swipe_mstatus);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.lite_blue));

        Intent i=getIntent();
        me=i.getStringExtra("mainteid");

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        loadmainte();
                        swipe.setRefreshing(false);

                    }
                }, 2000);
                //swipe.setRefreshing(false);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(maintenance_status.this));
        loadmainte();
    }

    public void loadmainte()
    {
        Api api= ApiClient.getClient().create(Api.class);
        Call<maintre_responce> call=api.mainpaid_details("mainpaiddetails",me);
        call.enqueue(new Callback<maintre_responce>() {
            @Override
            public void onResponse(Call<maintre_responce> call, Response<maintre_responce> response) {
                if (response.body().getSuccess()==200) {

                    li=response.body().getDe();
                    Collections.reverse(li);
                    ada=new main_status_adapter(li,maintenance_status.this);
                    recyclerView.setAdapter(ada);
                    LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(maintenance_status.this,R.anim.layout_anmimation_fall_down);
                    recyclerView.setLayoutAnimation(layoutAnimationController);
                }
                else {
                    Toast.makeText(maintenance_status.this, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onFailure(Call<maintre_responce> call, Throwable t) {
                Toast.makeText(maintenance_status.this, t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
