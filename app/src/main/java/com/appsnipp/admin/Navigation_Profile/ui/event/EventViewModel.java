package com.appsnipp.admin.Navigation_Profile.ui.event;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class EventViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EventViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Event fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}