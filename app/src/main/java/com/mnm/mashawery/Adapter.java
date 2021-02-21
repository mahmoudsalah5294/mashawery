package com.mnm.mashawery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.mnm.mashawery.ui.upcomming.UpcomingFragment;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Trip> values;
    private static final String TAG = "RecyclerView";
    Context context;
    Activity activity;
    final TripDataBase tripDataBase = TripDataBase.getInstance(context);

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.upcommingcard, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    public Adapter(List<Trip> values, Context context, Activity activity) {
        this.values = values;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.name.setText(values.get(position).getName());
        holder.date.setText(values.get(position).getTripdate());
        holder.time.setText(values.get(position).getTriptime());
        holder.from.setText(values.get(position).getStartpoint());
        holder.to.setText(values.get(position).getEndpoint());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripDataBase.tripDAO()
                        .deleteTrip(holder.name.getText().toString())
                        .subscribeOn(Schedulers.computation())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(context,MainActivity.class);
                               context.startActivity(intent);
                            }

                            @Override
                            public void onComplete() {
                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            }
                        });

            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripDataBase.tripDAO().editTrip(holder.name.getText().toString()).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Trip>() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                            }
                            @Override
                            public void onSuccess(@io.reactivex.annotations.NonNull Trip trip) {
                                Bundle args = new Bundle();
                                args.putString("Name", trip.getName());
                                args.putString("from", trip.getStartpoint());
                                args.putString("to", trip.getEndpoint());
                                args.putString("date", trip.getTripdate());
                                args.putString("time", trip.getTriptime());
                                args.putString("way", trip.getWay());
                                args.putString("repeated", trip.getRepeatation());
                                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                                EditFragment editFragment = new EditFragment(context);
                                editFragment.setArguments(args);
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Rec,editFragment).addToBackStack(null).commit();
                            }
                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            }
                        });
            }
        });
        holder.notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView name;
        public TextView date;
        public TextView from;
        public TextView to;
        public Button start;
        public Button delete;
        public Button edit;
        public ImageView image4;
        public ImageView notes;
        public View layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            time = itemView.findViewById(R.id.time);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.from2);
            start = itemView.findViewById(R.id.Startbutton);
            delete = itemView.findViewById(R.id.DeleteButton);
            edit = itemView.findViewById(R.id.Editbutton);
            image4 = itemView.findViewById(R.id.imageView4);
            notes = itemView.findViewById(R.id.imageView6);
        }
    }
}
