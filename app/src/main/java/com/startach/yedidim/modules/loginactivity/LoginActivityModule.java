package com.startach.yedidim.modules.loginactivity;

import android.app.Activity;

import com.startach.yedidim.LoginActivity;
import com.startach.yedidim.LoginActivityViewModel;
import com.startach.yedidim.LoginActivityViewModelImpl;
import com.startach.yedidim.entities.authentication.AuthEntity;
import com.startach.yedidim.entities.notification.NotificationDeviceIdSyncer;
import com.startach.yedidim.modules.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yb34982 on 26/09/2017.
 */

@Module
public class LoginActivityModule {

    private final LoginActivity activity;

    public LoginActivityModule(LoginActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    LoginActivityViewModel provideLoginActivityViewModel(AuthEntity authEntity, NotificationDeviceIdSyncer notificationDeviceIdSyncer) {
        return new LoginActivityViewModelImpl(authEntity, notificationDeviceIdSyncer);
    }

    @Provides
    Activity providesActivity() {
        return activity;
    }
}
