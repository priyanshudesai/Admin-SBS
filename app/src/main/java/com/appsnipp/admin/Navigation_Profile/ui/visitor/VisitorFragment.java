package com.appsnipp.admin.Navigation_Profile.ui.visitor;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.appsnipp.admin.R;
import com.appsnipp.admin.visitior_recy.visitior_adapter;
import com.appsnipp.admin.visitior_recy.visitior_data;

import java.util.ArrayList;
import java.util.List;

public class VisitorFragment extends Fragment {
RecyclerView recyclerView;
List<visitior_data> li;
    private VisitorViewModel visitorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        visitorViewModel =
                ViewModelProviders.of(this).get(VisitorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visitor, container, false);
recyclerView=(RecyclerView) root.findViewById(R.id.visitior_recycle);
        li=new ArrayList<>();
        visitior_data data[]={new visitior_data("jethava kaushal","7383846827","21/04/2020","1:20pm","2:20pm","B-102")
        ,new visitior_data("mokariya kaushik","7383846827","21/04/2020","1:40pm","2:40pm","B-102")
        ,new visitior_data("desai priyanshu","7383846827","21/04/2020","2:20pm","3:50pm","C-103")
        ,new visitior_data("bhatt danika","7383846827","20/04/2020","1:20pm","2:20pm","B-108")
        ,new visitior_data("patel shakshi","7383846827","20/04/2020","4:20pm","9:20pm","B-109")};
        for(int i=0;i< data.length;i++){
            li.add(data[i]);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        visitior_adapter vi=new visitior_adapter(li);
        recyclerView.setAdapter(vi);


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
}