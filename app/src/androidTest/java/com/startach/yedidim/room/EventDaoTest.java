package com.startach.yedidim.room;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.startach.yedidim.Model.Event;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import io.reactivex.functions.Predicate;
import io.reactivex.subscribers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
/**
 * Created by yb34982 on 28/01/2018.
 */

public class EventDaoTest  {
    AppDatabase mDatabase;


    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @Test
    public void getAll() throws Exception {
        assertEquals(2,2);
    }

    @Test
    public void insertAll() throws Exception {
        TestSubscriber testSubscriber = new TestSubscriber();
        final int caseInTemp = new Random().nextInt(10);
        final Event event = new Event("abcd",caseInTemp, 32.186414, 34.889700);
        final Event event2 = new Event("abcd2",caseInTemp, 30.2, 34.2);

//        mDatabase.eventDao().insertAll(new Event("abcd","sent"));
        mDatabase.eventDao().insertAll(event2,event);
        mDatabase.eventDao().getAll().subscribe(event1 -> System.out.println("EVENTTTT : " + event1.toString()));
        System.out.println("Count : " + mDatabase.eventDao().count());
        assertEquals(1,1);
    }

    @Test
    public void testEvent() throws Exception {
        final Event event2 = new Event("abcd2",3, 30.2, 34.2);
        mDatabase.eventDao().insertAll(event2);
        mDatabase.eventDao().getEvent("abcd2")
                .test()
                .assertValue(new Predicate<Event>() {
                    @Override
                    public boolean test(Event event) throws Exception {
                        return event.getDetails().getGeo().getLat().equals(30.2);
                    }
                });


    }

}