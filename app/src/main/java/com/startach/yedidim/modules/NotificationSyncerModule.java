package com.startach.yedidim.modules;


import android.content.Context;

import com.startach.yedidim.entities.notification.NotificationDeviceIdSyncer;
import com.startach.yedidim.entities.usermanagement.UserManager;
import com.startach.yedidim.network.VolunteerApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class NotificationSyncerModule {

    @Provides
    @Singleton
    NotificationDeviceIdSyncer providesNotificationDeviceIdSyncer(VolunteerApi volunteerApi, UserManager userManager) {
        return new NotificationDeviceIdSyncer(volunteerApi, userManager);
    }

    @Provides
    @Singleton
    VolunteerApi providesVolunteerApi(Retrofit retrofit, @Named("cloud_functions") Retrofit cloudFunctionsRetrofit) {
        return new VolunteerApi(retrofit, cloudFunctionsRetrofit);
    }

    @Provides
    @Singleton
    UserManager providesUserManager(Context context, VolunteerApi volunteerApi) {
        return new UserManager(context, volunteerApi);
    }
}
