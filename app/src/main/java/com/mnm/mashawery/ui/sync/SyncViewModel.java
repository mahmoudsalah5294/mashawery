package com.mnm.mashawery.ui.sync;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SyncViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SyncViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is sync fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}