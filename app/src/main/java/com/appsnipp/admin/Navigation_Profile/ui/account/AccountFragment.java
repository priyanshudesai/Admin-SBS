package com.appsnipp.admin.Navigation_Profile.ui.account;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.appsnipp.admin.R;
import com.appsnipp.admin.accout_fragment.bill_details;
import com.appsnipp.admin.accout_fragment.maintence_details;
import com.appsnipp.admin.apiinterface.Api;
import com.appsnipp.admin.apiinterface.ApiClient;
import com.appsnipp.admin.apiinterface.CommanResponse;
import com.appsnipp.admin.apiinterface.responce.event_responce;
import com.appsnipp.admin.event_recycle_view.event_adapter;
import com.appsnipp.admin.res_book_res.book_res_act;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    TabLayout tabLayout;
    EditText fname,famt,fdate;
    ViewPager viewPager;
    FragmentManager manager;
    Fragment fragment;
    FloatingActionButton fb1,fb2;
    AlertDialog.Builder builder;
    private AccountViewModel accountViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        fb1 = root.findViewById(R.id.fab_acc_bill);
        fb2= root.findViewById(R.id.fab_acc_main);
        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "bill", Toast.LENGTH_SHORT).show();
                builder= new AlertDialog.Builder(getContext());
                LayoutInflater inflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v=inflater.inflate(R.layout.accbill_add,null);
                builder.setView(v);
                builder.setCancelable(true);
                AlertDialog alert=builder.create();

                fname=(EditText) v.findViewById(R.id.fbill_name);
                famt=(EditText) v.findViewById(R.id.fbill_amt);
                fdate=(EditText) v.findViewById(R.id.fbill_date);
                String s =new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(new Date());
                fdate.setText(s);
                fdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                                            fdate.setText(String.format("%02d-%02d-%04d",dayOfMonth,monthOfYear+1,year));
                                            //e1.setText(dayOfMonth+"-"+(monthOfYear + 1)+"-"+year);

                                        }

                                    }, mYear, mMonth, mDay);
                            datePickerDialog.show();




                        }
                    }
                });

                v.findViewById(R.id.fbill_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sfname=fname.getText().toString();
                        String sfamt=famt.getText().toString();
                        String sfdate=fdate.getText().toString();


                        Api api= ApiClient.getClient().create(Api.class);
                        Call<CommanResponse> call= api.accbillinsert("billinsert",sfname,sfamt,sfdate);
                        call.enqueue(new Callback<CommanResponse>() {
                            @Override
                            public void onResponse(Call<CommanResponse> call, Response<CommanResponse> response) {
                                if (response.body().getSuccess()==200) {
                                    Toast.makeText(getContext(), response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                                    alert.dismiss();
                                }
                                else {
                                    Toast.makeText(getContext(), response.body().getMessage()+"", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<CommanResponse> call, Throwable t) {
                                Toast.makeText(getContext(), t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


                alert.show();
            }
        });

        fb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "account", Toast.LENGTH_SHORT).show();

            }
        });


        tabLayout = root.findViewById(R.id.accout_tablayout);

        viewPager = root.findViewById(R.id.tablayout_account_viewpager);
        manager = getFragmentManager();
        viewPager.setAdapter(new AccountFragment.adapter(manager));
        viewPager.setOffscreenPageLimit(2);
        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return root;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public class adapter extends FragmentStatePagerAdapter {

        public adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = null;
            if (position == 0) {

                fragment = new bill_details();


            }
            if (position == 1) {

                fragment = new maintence_details();

            }


            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}