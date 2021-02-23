package com.mnm.mashawery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NoteActivity extends AppCompatActivity {
    Button addnotes;
    TextView Notes;
    EditText notestxt;
    RecyclerView recyclerView;
    List<String> mynotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        TripDataBase tripDataBase = TripDataBase.getInstance(this);
        addnotes=(Button)findViewById(R.id.addbtn);
        notestxt=(EditText)findViewById(R.id.notetxt);
        Notes=(TextView)findViewById(R.id.textView2);
        addnotes=(Button)findViewById(R.id.addbtn);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        String tripname=getIntent().getStringExtra("Tripname");
        tripDataBase = TripDataBase.getInstance(this);
        tripDataBase.tripDAO().getTrip().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Trip>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull List<Trip> trips) {
                        for(Trip t: trips){
                            if(t.getName().equals(tripname)){
                             /*   NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(),t.getNotes());
                               // mynotes=t.getNotes();
                                recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recyclerView.setAdapter(noteAdapter);
                                break;*/
                            }

                        }

                    }
                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("UPCOMING", "onError: ");
                    }
                });
        addnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}