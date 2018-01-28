package com.startach.yedidim.modules;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.startach.yedidim.modules.network.NetworkModule;
import com.startach.yedidim.room.RoomModule;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by yb34982 on 26/09/2017.
 */

public class App extends Application {
    private AppComponent component;

    @Inject Timber.Tree timberTree;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .roomModule(new RoomModule(this))
                .notificationSyncerModule(new NotificationSyncerModule())
                .build();
        component.inject(this);

        initLog();
    }

    private void initLog() {
        Timber.plant(timberTree);
    }

    public AppComponent getComponent() {
        return component;
    }
}
