package com.startach.yedidim.modules;

import android.app.Application;

import com.startach.yedidim.modules.network.NetworkModule;

import javax.inject.Inject;

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
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
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
