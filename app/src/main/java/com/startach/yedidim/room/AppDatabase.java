package com.startach.yedidim.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.startach.yedidim.Model.Event;

/**
 * Created by yb34982 on 28/01/2018.
 */

@Database(entities = {Event.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
}