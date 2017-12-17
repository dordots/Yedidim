package com.startach.yedidim.modules;


import android.content.Context;

import com.startach.yedidim.entities.notification.NotificationDeviceIdSyncer;
import com.startach.yedidim.entities.usermanagement.UserManager;
import com.startach.yedidim.network.VolunteerApi;
import com.startach.yedidim.network.YedidimApiService;
import com.startach.yedidim.network.YedidimCloudFunctionsApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationSyncerModule {

    @Provides
    @Singleton
    NotificationDeviceIdSyncer providesNotificationDeviceIdSyncer(VolunteerApi volunteerApi, UserManager userManager) {
        return new NotificationDeviceIdSyncer(volunteerApi, userManager);
    }

    @Provides
    @Singleton
    VolunteerApi providesVolunteerApi(YedidimCloudFunctionsApiService yedidimCloudFunctionsApiService, YedidimApiService yedidimApiService) {
        return new VolunteerApi(yedidimApiService, yedidimCloudFunctionsApiService);
    }

    @Provides
    @Singleton
    UserManager providesUserManager(Context context, VolunteerApi volunteerApi) {
        return new UserManager(context, volunteerApi);
    }
}
