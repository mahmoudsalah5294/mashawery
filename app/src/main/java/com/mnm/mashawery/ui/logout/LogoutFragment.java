package com.mnm.mashawery.ui.logout;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mnm.mashawery.R;
import com.mnm.mashawery.databinding.FragmentLogoutBinding;
import com.mnm.mashawery.registration.Login;

public class LogoutFragment extends Fragment {

    private LogoutViewModel logoutViewModel;
    FragmentLogoutBinding logoutBinding;
    //FirebaseAuth logoutAuth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logoutViewModel =
                new ViewModelProvider(this).get(LogoutViewModel.class);

        logoutBinding = FragmentLogoutBinding.inflate(inflater,container,false);
        View root = logoutBinding.getRoot();
        final TextView textView = root.findViewById(R.id.text_logout);
        logoutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        //logoutAuth = FirebaseAuth.getInstance();

        logoutBinding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), Login.class));
            }
        });

        return root;
    }


}