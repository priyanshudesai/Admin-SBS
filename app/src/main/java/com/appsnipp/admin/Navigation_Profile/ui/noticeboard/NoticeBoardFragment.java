package com.appsnipp.admin.Navigation_Profile.ui.noticeboard;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appsnipp.admin.R;
import com.appsnipp.admin.apiinterface.Api;
import com.appsnipp.admin.apiinterface.ApiClient;
import com.appsnipp.admin.apiinterface.CommanResponse;
import com.appsnipp.admin.apiinterface.responce.loginresponce;
import com.appsnipp.admin.apiinterface.responce.notice_responce;
import com.appsnipp.admin.apiinterface.responce_get_set.User;
import com.appsnipp.admin.apiinterface.responce_get_set.notice_get_set;
import com.appsnipp.admin.event_recycle_view.event_data;
import com.appsnipp.admin.notice_recycle.notice_adapter;
import com.appsnipp.admin.notice_recycle.notice_data;
import com.appsnipp.admin.resource_list.resource_adapter;
import com.appsnipp.admin.storage.sareprefrencelogin;
import com.appsnipp.admin.visitior_recy.visitior_adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeBoardFragment extends Fragment {
    RecyclerView recyclerView;
    List<notice_get_set> li;
    FloatingActionButton f;
    AlertDialog.Builder builder;
    EditText ntf_head,ntf_desc;
    Button Sv;
    SwipeRefreshLayout swipe;
    notice_adapter ada;
    String ntfs_head,ntfs_desc;
    private NoticeBoardViewModel noticeBoardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        noticeBoardViewModel =
                ViewModelProviders.of(this).get(NoticeBoardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notice, container, false);
//        final TextView textView = root.findViewById(R.id.text_notice);
        noticeBoardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //              textView.setText(s);
            }
        });
//        li=new ArrayList<>();
//
//        notice_data data2[]={new notice_data("Last Notice for Maintenance Due","Defaulters post Oct 12th will be levied a final of 10%","K.L.Mokariya","14 min.ago,11:20 AM")
//                ,new notice_data("Lift Maintenance","Inform that lifts are under maintanance on 10/2/2019 from 3 to 5 pm kindly cooperat.","P.D.Desai","14 min.ago,11:20 AM")
//                ,new notice_data("Upcoming Road Maintenance","Walkway before block A will be closed down for annual maintenance.","K.J.Jethva","14 min.ago,11:20 AM")
//                ,new notice_data("Upcoming Road Maintenance","Walkway before block A will be closed down for annual maintenance.","K.J.Jethva","14 min.ago,11:20 AM")
//                ,new notice_data("Upcoming Road Maintenance","Walkway before block A will be closed down for annual maintenance.","K.J.Jethva","14 min.ago,11:20 AM")};
//
//
//            for(int i=0;i< data2.length;i++){
//                li.add(data2[i]);
//            }
        recyclerView=(RecyclerView) root.findViewById(R.id.noticerecycle);
        swipe=(SwipeRefreshLayout) root.findViewById(R.id.swipe_notice);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadnotice();
                swipe.setRefreshing(false);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       loadnotice();


        f=(FloatingActionButton) root.findViewById(R.id.fab_notice);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder= new AlertDialog.Builder(getContext());
                LayoutInflater inflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v=inflater.inflate(R.layout.notice_form,null);
                builder.setView(v);
                builder.setCancelable(true);
                AlertDialog alert=builder.create();
                ntf_head=(EditText) v.findViewById(R.id.ntc_head);
                ntf_desc=(EditText) v.findViewById(R.id.ntc_desc);

                Sv=(Button) v.findViewById(R.id.ntc_save);
                Sv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ntfs_head=ntf_head.getText().toString().trim();
                        ntfs_desc=ntf_desc.getText().toString().trim();
                        User user= sareprefrencelogin.getInstance(getContext()).getuser();
                        String name=user.getFname()+" "+user.getLname();
                        String s =new SimpleDateFormat("DD-MM-yyyy hh:mm a", Locale.US).format(new Date());


                        Api api = ApiClient.getClient().create(Api.class);
                        Call<CommanResponse> call=api.noticeentry("noticeentry",ntfs_head,ntfs_desc,name,s);

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
    private void runLayoutanimation (RecyclerView recyclerView)
    {
        Context context=recyclerView.getContext();
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_anmimation_fall_down);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
    public void loadnotice()
    {
        Api api=ApiClient.getClient().create(Api.class);
        Call<notice_responce> call=api.noticedetail("noticedetail");
        call.enqueue(new Callback<notice_responce>() {
            @Override
            public void onResponse(Call<notice_responce> call, Response<notice_responce> response) {
                if (response.body().getSuccess()==200) {

                    li=response.body().getDe();
                    Collections.reverse(li);
                    ada=new notice_adapter(getContext(),li);
                    recyclerView.setAdapter(ada);
                }
                else {
                    Toast.makeText(getContext(), response.body().getMessage()+"", Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onFailure(Call<notice_responce> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

    }

}