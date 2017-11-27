package com.startach.yedidim.modules.eventinfoactivity;

import android.app.Activity;

import com.startach.yedidim.EventInfoActivity;
import com.startach.yedidim.EventInfoViewModel;
import com.startach.yedidim.EventInfoViewModelImpl;
import com.startach.yedidim.modules.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class EventInfoActivityModule {

    private final EventInfoActivity activity;

    public EventInfoActivityModule(EventInfoActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    EventInfoViewModel provideEventInfoActivityViewModel() {
        return new EventInfoViewModelImpl();
    }

    @Provides
    Activity providesActivity() {
        return activity;
    }
}
