package com.mnm.mashawery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.mnm.mashawery.ui.upcomming.UpcomingFragment;
import java.util.Calendar;
import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddTrip extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener dateSetListener;
    ImageView calenderImage, alarmImage;
    final Context context = AddTrip.this;
    EditText tripname;
    AutoCompleteTextView frompoint;
    AutoCompleteTextView topoint;
    Spinner repeated;
    Spinner way;
    String thedate;
    String thetime;
    Button add;
    Button cancel;
    boolean Roundtrip=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        tripname = (EditText) findViewById(R.id.tripNameTxt);
        frompoint = (AutoCompleteTextView) findViewById(R.id.fromPoint);
        topoint = (AutoCompleteTextView) findViewById(R.id.toPoint);
        repeated = (Spinner) findViewById(R.id.repeatedSpinner);
        way = (Spinner) findViewById(R.id.tripSpinner);
        add = (Button) findViewById(R.id.button);
        cancel=(Button)findViewById(R.id.button2);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        boolean is24Hour = false;
        final TripDataBase tripDataBase = TripDataBase.getInstance(this);
        DialogPicker dialogPicker = new DialogPicker();

        calenderImage = findViewById(R.id.calenderImage);
        alarmImage = findViewById(R.id.alarmImage);
        calenderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPicker.datePickerDialog(context, year, month, day);
            }
        });

        alarmImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPicker.timePickerDialog(context, hour, minute, is24Hour);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theRepeatation=repeated.getSelectedItem().toString()+","+String.valueOf(repeated.getSelectedItemPosition());
                String theway=way.getSelectedItem().toString()+","+String.valueOf(way.getSelectedItemPosition());
                if(way.getSelectedItem().toString().equals("Round Trip"))
                    Roundtrip=true;
                tripDataBase.tripDAO()
                        .insertTrip(new Trip(tripname.getText().toString(), frompoint.getText().toString(), topoint.getText().toString(), DialogPicker.date, DialogPicker.time, theRepeatation, theway, "upcoming"))
                        .subscribeOn(Schedulers.computation())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
                if (Roundtrip){
                    Roundtrip=false;
                    tripDataBase.tripDAO()
                            .insertTrip(new Trip(tripname.getText().toString()+"BacK", topoint.getText().toString(), frompoint.getText().toString(), DialogPicker.date, DialogPicker.time, theRepeatation, theway, "upcoming"))
                            .subscribeOn(Schedulers.computation())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onComplete() {

                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }
                            });
                }
                Intent intent=new Intent(AddTrip.this,MainActivity.class);
                startActivity(intent);
            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        });
    }


}