package com.startach.yedidim.modules.testactivity;


import android.app.Activity;

import com.startach.yedidim.TestActivity;
import com.startach.yedidim.modules.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class TestModule {
    private final TestActivity activity;

    public TestModule(TestActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    Activity providesActivity() {
        return activity;
    }
}
