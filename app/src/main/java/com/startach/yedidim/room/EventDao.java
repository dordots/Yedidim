package com.startach.yedidim.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
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

    @Query("SELECT * From event WHERE `status` = 'assigned' LIMIT 1")
    Event getOpenEvent();

    @Query("SELECT count(*) FROM event")
    int count();

    @Query("UPDATE event SET `status` = :status WHERE `key` = :eventKey")
    int updateEventStatus(String eventKey, String status);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Event... events);

}
