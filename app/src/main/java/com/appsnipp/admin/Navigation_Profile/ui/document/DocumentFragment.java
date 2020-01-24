package com.appsnipp.admin.Navigation_Profile.ui.document;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsnipp.admin.R;
import com.appsnipp.admin.document_recycle.document_adapter;
import com.appsnipp.admin.document_recycle.documnet_data;

import java.util.ArrayList;
import java.util.List;

public class DocumentFragment extends Fragment {
    RecyclerView recyclerView;
    List<documnet_data> li;
    private DocumentViewModel documentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        documentViewModel =
                ViewModelProviders.of(this).get(DocumentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_document, container, false);
        li=new ArrayList<>();
        recyclerView=(RecyclerView) root.findViewById(R.id.document_recycle);
        documnet_data data[]={new documnet_data("kaushal")
        ,new documnet_data("kaushik")
        ,new documnet_data("priyanshu"),
                new documnet_data("kaushal")
                ,new documnet_data("kaushik")
                ,new documnet_data("priyanshu"),
                new documnet_data("kaushal")
                ,new documnet_data("kaushik")
                ,new documnet_data("priyanshu")};
        for(int i=0;i< data.length;i++){
            li.add(data[i]);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        document_adapter ada=new document_adapter(li);
        recyclerView.setAdapter(ada);
        return root;
    }
}