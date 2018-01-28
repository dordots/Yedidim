package com.startach.yedidim.repository;

import com.startach.yedidim.Model.Event;
import com.startach.yedidim.room.EventDao;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * Created by yb34982 on 28/01/2018.
 */

public class EventsRepositoryImpl implements EventsRepository {
    private EventDao eventDao;

    @Inject
    public EventsRepositoryImpl(EventDao eventDao){
        this.eventDao = eventDao;
    }

    @Override
    public Event getOpenEvent() {
        return eventDao.getOpenEvent();
    }

    @Override
    public void storeOpenEvent(@NotNull Event event) {
        eventDao.insertAll(event);
    }

    @Override
    public void closeOpenEvent(@NotNull Event event, String newStatus) {
        eventDao.updateEventStatus(event.getKey(),newStatus);
    }
}
