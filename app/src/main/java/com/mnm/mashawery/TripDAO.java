package com.mnm.mashawery;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface TripDAO {
    @Insert
    Completable insertTrip(Trip trip);
    @Query("select * from TripTable")
    Single<List<Trip>> getTrip();
    @Query("DELETE FROM TripTable WHERE name = :name")
    Completable deleteTrip(String name);
    @Query("select * from TripTable where name = :name ")
    Single <Trip>editTrip(String name);

}
