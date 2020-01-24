package com.appsnipp.admin.Navigation_Profile.ui.resource;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsnipp.admin.R;
import com.appsnipp.admin.resource_list.adapter;
import com.appsnipp.admin.resource_list.data;

import java.util.ArrayList;
import java.util.List;

public class ResourceFragment extends Fragment {

    RecyclerView recyclerView;
    List<data> li;

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
        li=new ArrayList<>();
        data azad[]={new data("b-101","club","kausahl","kausahl jethava",R.drawable.club),new data("b-102","club","kaushik","kausahik mokariya",R.drawable.club)
                ,new data("b-102","club","priyanshu","priyanshu desai",R.drawable.club)
                ,new data("b-102","club","aditya","aditya panchal",R.drawable.club)
                ,new data("b-102","club","shakshi","shakshi patel",R.drawable.club)
                ,new data("b-102","club","danika","danika bhatt",R.drawable.club)
                ,new data("b-102","club","kuldeep","kuldeep jethava",R.drawable.club)};

        for(int i=0;i<azad.length;i++)

        {
            li.add(azad[i]);

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter ev=new adapter(li);
        recyclerView.setAdapter(ev);


        return root;
    }
}