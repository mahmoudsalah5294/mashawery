package com.mnm.mashawery.ui.upcomming;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mnm.mashawery.AddTrip;
import com.mnm.mashawery.R;

public class UpcomingFragment extends Fragment {


    FloatingActionButton floatingActionButton;
    private UpcommingViewModel upcommingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        upcommingViewModel =
                new ViewModelProvider(this).get(UpcommingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_upcomming, container, false);



        floatingActionButton = root.findViewById(R.id.floatingActionButton);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddTrip.class));
            }
        });
        final TextView textView = root.findViewById(R.id.text_upcomming);
        upcommingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}