package com.appsnipp.admin.Navigation_Profile.ui.document;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.appsnipp.admin.R;
import com.appsnipp.admin.apiinterface.Api;
import com.appsnipp.admin.apiinterface.ApiClient;
import com.appsnipp.admin.apiinterface.CommanResponse;
import com.appsnipp.admin.apiinterface.responce.document_responce;
import com.appsnipp.admin.apiinterface.responce.event_responce;
import com.appsnipp.admin.apiinterface.responce_get_set.document_get_set;
import com.appsnipp.admin.document_recycle.FileUtil;
import com.appsnipp.admin.document_recycle.document_adapter;
import com.appsnipp.admin.document_recycle.documnet_data;
import com.appsnipp.admin.event_recycle_view.event_adapter;
import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentFragment extends Fragment {

    RecyclerView recyclerView;
    List<document_get_set> li;
    Uri filepath;
    document_adapter ev;
    String path;
    SwipeRefreshLayout swipe;
    private static final int REQUEST_CALL = 1;
    FloatingActionButton f;
    private DocumentViewModel documentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        documentViewModel =
                ViewModelProviders.of(this).get(DocumentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_document, container, false);

        recyclerView=(RecyclerView) root.findViewById(R.id.document_recycle);
        f=(FloatingActionButton) root.findViewById(R.id.fab_doc);
        swipe=(SwipeRefreshLayout) root.findViewById(R.id.swipe_doc);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.lite_blue));

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        loaddocument();
                        swipe.setRefreshing(false);

                    }
                }, 2000);
                // swipe.setRefreshing(false);
            }
        });


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
       loaddocument();


        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               launchPicker();
            }
        });
        return root;
    }

    private void uploadFile() {


        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CALL);
        }else {

            File pdfdata = new File(FileUtil.getPath(filepath, getContext()));
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), pdfdata);
            MultipartBody.Part document_file = MultipartBody.Part.createFormData("document_file", pdfdata.getName(), requestBody);

            RequestBody comp = RequestBody.create(MediaType.parse("text/plain"), "docupload");

            //  Toast.makeText(getContext(), "API", Toast.LENGTH_SHORT).show();
            Api api = ApiClient.getClient().create(Api.class);
            Call<CommanResponse> call = api.docuplaod(comp, document_file);
            call.enqueue(new Callback<CommanResponse>() {
                @Override
                public void onResponse(Call<CommanResponse> call, Response<CommanResponse> response) {
                    if (response.body().getSuccess() == 200) {
                        Toast.makeText(getContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CommanResponse> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    private void launchPicker()
    {
//
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select a PDF file"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            path = filepath.getPath();
           // Toast.makeText(getContext(), path, Toast.LENGTH_SHORT).show();
            uploadFile();
        }
    }


    public void loaddocument()
    {
        Api api= ApiClient.getClient().create(Api.class);
        Call<document_responce> call= api.documentdetailsl("documentdetail");
        call.enqueue(new Callback<document_responce>() {
            @Override
            public void onResponse(Call<document_responce> call, Response<document_responce> response) {
                if (response.body().getSuccess()==200) {
                    li=response.body().getDe();
                    Collections.reverse(li);
                    ev=new document_adapter(li,getContext());

                    recyclerView.setAdapter(ev);
                    LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_animation_from_right);
                    recyclerView.setLayoutAnimation(layoutAnimationController);
                }
                else {
                    Toast.makeText(getContext(), response.body().getMessage()+"", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<document_responce> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });



    }




}