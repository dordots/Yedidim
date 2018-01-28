package com.startach.yedidim.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.startach.yedidim.Model.Event;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by yb34982 on 28/01/2018.
 */
@Dao
public interface EventDao {
    @Query("SELECT * FROM event")
    Flowable<Event> getAll();

    @Query("SELECT * FROM event WHERE `key` = :key LIMIT 1")
    Single<Event> getEvent(String key);

    @Query("SELECT count(*) FROM event")
    int count();

    @Insert
    void insertAll(Event... events);
}
