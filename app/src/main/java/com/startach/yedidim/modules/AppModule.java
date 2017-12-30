package com.startach.yedidim.modules;

import android.app.Application;
import android.content.Context;

import com.startach.yedidim.BuildConfig;
import com.startach.yedidim.Navigator;
import com.startach.yedidim.modules.log.ReleaseTimberTree;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by yb34982 on 25/09/2017.
 */

@Module
public class AppModule {

    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Context providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Timber.Tree providesTimber() {
        return BuildConfig.DEBUG ?
                new Timber.DebugTree() :
                new ReleaseTimberTree();
    }

    @Provides
    @Singleton
    Navigator providesNavigator() {
        return new Navigator(mApplication);
    }
}