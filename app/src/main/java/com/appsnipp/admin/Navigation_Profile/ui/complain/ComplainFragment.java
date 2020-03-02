package com.appsnipp.admin.Navigation_Profile.ui.complain;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.admin.BuildConfig;
import com.appsnipp.admin.R;
import com.appsnipp.admin.apiinterface.Api;
import com.appsnipp.admin.apiinterface.ApiClient;
import com.appsnipp.admin.apiinterface.responce.cmp_responce;
import com.appsnipp.admin.apiinterface.responce.event_responce;
import com.appsnipp.admin.apiinterface.responce_get_set.cmp_get_set;
import com.appsnipp.admin.apiinterface.responce_get_set.event_get_set;
import com.appsnipp.admin.camera.FileCompressor;
import com.appsnipp.admin.cmp_recycle.cmp_adapter;
import com.appsnipp.admin.event_recycle_view.event_adapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ComplainFragment extends Fragment {
    RecyclerView recyclerView;

    cmp_adapter ev;
    SwipeRefreshLayout swipe;
    List<cmp_get_set> li;


    private ComplainViewModel complainViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        complainViewModel =
                ViewModelProviders.of(this).get(ComplainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_complain, container, false);
        recyclerView=(RecyclerView) root.findViewById(R.id.complainrecycle);
        swipe=(SwipeRefreshLayout) root.findViewById(R.id.swipe_complain);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.lite_blue));

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        loadcmp();
                        swipe.setRefreshing(false);

                    }
                }, 2000);
                // swipe.setRefreshing(false);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadcmp();
        return root;
    }

    public void loadcmp()
    {
        Api api= ApiClient.getClient().create(Api.class);
        Call<cmp_responce> call= api.cmp_details("complaindetail");
        call.enqueue(new Callback<cmp_responce>() {
            @Override
            public void onResponse(Call<cmp_responce> call, Response<cmp_responce> response) {
                if (response.body().getSuccess()==200) {
                    li=response.body().getDe();
                    Collections.reverse(li);
                    ev=new cmp_adapter(getContext(),li);

                    recyclerView.setAdapter(ev);
                    LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_animation_from_right);
                    recyclerView.setLayoutAnimation(layoutAnimationController);
                }
                else {
                    Toast.makeText(getContext(), response.body().getMessage()+"", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<cmp_responce> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });



    }

}