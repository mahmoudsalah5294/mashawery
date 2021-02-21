package com.mnm.mashawery;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class DialogPicker {
    public static String time;
    public  static String date;
    public void datePickerDialog(Context context, int year, int month, int day){
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        Toast.makeText(context, day+"/"+month+"/"+year, Toast.LENGTH_SHORT).show();
                         date=day+"/"+month+"/"+year;
                    }
                },year,month,day);
        datePickerDialog.show();
    }

    public void timePickerDialog(Context context, int hour, int min, boolean is24Hour){
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(context, hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();
                time=hourOfDay+":"+minute;
            }
        },hour,min,is24Hour);
        timePickerDialog.show();
    }
}
