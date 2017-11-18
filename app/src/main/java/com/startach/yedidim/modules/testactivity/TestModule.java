package com.startach.yedidim.modules.testactivity;


import android.app.Activity;

import com.startach.yedidim.TestActivity;
import com.startach.yedidim.entities.usermanagement.UserManager;
import com.startach.yedidim.entities.usermanagement.VolunteerLocationUpdater;
import com.startach.yedidim.modules.ActivityScope;
import com.startach.yedidim.network.VolunteerApi;

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
    VolunteerLocationUpdater providesVolunteerLocationUpdater(VolunteerApi volunteerApi, UserManager userManager) {
        return new VolunteerLocationUpdater(userManager, volunteerApi);
    }

    @ActivityScope
    @Provides
    Activity providesActivity() {
        return activity;
    }
}
