package com.startach.yedidim.room;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.startach.yedidim.repository.EventsRepository;
import com.startach.yedidim.repository.EventsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yb34982 on 28/01/2018.
 */
@Module
public class RoomModule {
    Application application;

    public RoomModule(Application application) {
        this.application = application;
    }
    @Provides
    @Singleton
    AppDatabase providesEventDatabase() {
        return Room.databaseBuilder(application.getApplicationContext(), AppDatabase.class, "event_db")
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    EventDao providesEventDao(AppDatabase appDatabase){
        return appDatabase.eventDao();
    }

    @Provides
    @Singleton
    EventsRepository provideEventRepository(EventDao eventDao){
        return new EventsRepositoryImpl(eventDao);
    }
}
