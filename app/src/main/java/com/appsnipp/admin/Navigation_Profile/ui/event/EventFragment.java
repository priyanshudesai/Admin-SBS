package com.appsnipp.admin.Navigation_Profile.ui.event;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.appsnipp.admin.LoginActivity;
import com.appsnipp.admin.Navigation_Profile.Navigation_Activity;
import com.appsnipp.admin.R;
import com.appsnipp.admin.apiinterface.Api;
import com.appsnipp.admin.apiinterface.ApiClient;
import com.appsnipp.admin.apiinterface.CommanResponse;
import com.appsnipp.admin.apiinterface.responce.event_responce;
import com.appsnipp.admin.apiinterface.responce.loginresponce;
import com.appsnipp.admin.apiinterface.responce.visidetail_responce;
import com.appsnipp.admin.apiinterface.responce_get_set.event_get_set;
import com.appsnipp.admin.event_recycle_view.event_adapter;
import com.appsnipp.admin.event_recycle_view.event_data;
import com.appsnipp.admin.storage.sareprefrencelogin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton f;
    event_adapter ev;
    SwipeRefreshLayout swipe;
    AlertDialog.Builder builder;
    List<event_get_set> li;
    EditText etf_name,etf_place,etf_member,etf_date,etf_time;
    Button Sv;
    String etfs_name,etfs_place,etfs_member,etfs_date,etfs_time;
    private EventViewModel eventViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventViewModel =
                ViewModelProviders.of(this).get(EventViewModel.class);
        View root = inflater.inflate(R.layout.fragment_event, container, false);
        recyclerView=(RecyclerView) root.findViewById(R.id.event_recycle);
        swipe=(SwipeRefreshLayout) root.findViewById(R.id.swipe_event);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.lite_blue));

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        loadevent();
                        swipe.setRefreshing(false);
                        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_animation_from_right);
                        recyclerView.setLayoutAnimation(layoutAnimationController);
                    }
                }, 2000);
               // swipe.setRefreshing(false);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        li=new ArrayList<>();
//        event_data data[] ={new event_data("diwali","party plot","5 member","23/04/2019","2:45 pm")
//        ,new event_data("holi","play ground","6 member","23/06/2019","7:40 am")
//        ,new event_data("new year","club house","6 member","28/04/2019","7:90 am")
//        ,new event_data("navratri","play ground","All Family","22/09/2019","4:40 am")
//                ,new event_data("new year","club house","6 member","28/04/2019","7:90 am")
//                ,new event_data("holi","play ground","6 member","23/06/2019","7:40 am")
//        };
//        for(int i=0;i< data.length;i++){
//            li.add(data[i]);
//        }
        loadevent();


        f=(FloatingActionButton) root.findViewById(R.id.fab_event);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder= new AlertDialog.Builder(getContext());
                LayoutInflater inflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v=inflater.inflate(R.layout.event_form,null);
                builder.setView(v);
                builder.setCancelable(true);
                AlertDialog alert=builder.create();
                etf_name=(EditText) v.findViewById(R.id.evt_name);
                etf_place=(EditText) v.findViewById(R.id.evt_place);
                etf_member=(EditText) v.findViewById(R.id.evt_member);
                etf_date=(EditText) v.findViewById(R.id.evt_date);
                etf_time=(EditText) v.findViewById(R.id.evt_time);
                Sv=(Button) v.findViewById(R.id.evt_save);


                etf_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if(b){




                            final Calendar c = Calendar.getInstance();



                            // Get Current Time

                            int mHour = c.get(Calendar.HOUR_OF_DAY);
                            int mMinute = c.get(Calendar.MINUTE);

                            // Launch Time Picker Dialog
                            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                    new TimePickerDialog.OnTimeSetListener() {

                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay,
                                                              int minute) {

//                                    if (hourOfDay>13){
//
//                                        e.setText((hourOfDay-12) + ":" + minute+" PM");
//                                    }
//                                    else {
//                                        if(hourOfDay==0){
//                                            hourOfDay=12;
//                                        }
//                                        e.setText(hourOfDay + ":" + minute+" AM");
//
//                                    }

                                            boolean isPM = (hourOfDay >= 12);
                                            etf_time.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
                                        }
                                    }, mHour, mMinute, false);
                            timePickerDialog.show();





                        }
                    }
                });
                etf_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if(b){



                            // Get Current Date
                            final Calendar c = Calendar.getInstance();
                            int mYear = c.get(Calendar.YEAR);
                            int mMonth = c.get(Calendar.MONTH);
                            int mDay = c.get(Calendar.DAY_OF_MONTH);




                            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                    new DatePickerDialog.OnDateSetListener() {

                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {
                                            etf_date.setText(String.format("%02d-%02d-%04d",dayOfMonth,monthOfYear+1,year));
                                            //e1.setText(dayOfMonth+"-"+(monthOfYear + 1)+"-"+year);

                                        }

                                    }, mYear, mMonth, mDay);
                            datePickerDialog.show();




                        }
                    }
                });


                Sv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etfs_name=etf_name.getText().toString().trim();
                        etfs_place=etf_place.getText().toString().trim();
                        etfs_member=etf_member.getText().toString().trim();
                        etfs_date=etf_date.getText().toString().trim();
                        etfs_time=etf_time.getText().toString().trim();







                        Api api = ApiClient.getClient().create(Api.class);
                        Call<CommanResponse> call=api.evententry("evententry",etfs_name,etfs_place,etfs_member,etfs_date,etfs_time);

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
    public void loadevent()
    {
        Api api= ApiClient.getClient().create(Api.class);
        Call<event_responce> call= api.eventdetail("eventdetail");
        call.enqueue(new Callback<event_responce>() {
            @Override
            public void onResponse(Call<event_responce> call, Response<event_responce> response) {
                if (response.body().getSuccess()==200) {
                    li=response.body().getDe();
                    Collections.reverse(li);
                    ev=new event_adapter(getContext(),li);

                    recyclerView.setAdapter(ev);
                }
                else {
                    Toast.makeText(getContext(), response.body().getMessage()+"", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<event_responce> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });



    }
}