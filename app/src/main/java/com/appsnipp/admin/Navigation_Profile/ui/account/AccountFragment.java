package com.appsnipp.admin.Navigation_Profile.ui.account;

import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Toast;

import com.appsnipp.admin.R;
import com.appsnipp.admin.accout_fragment.bill_details;
import com.appsnipp.admin.accout_fragment.maintence_details;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class AccountFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentManager manager;
    Fragment fragment;
    FloatingActionButton fb1,fb2;

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
                Toast.makeText(getContext(), "bill", Toast.LENGTH_SHORT).show();
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