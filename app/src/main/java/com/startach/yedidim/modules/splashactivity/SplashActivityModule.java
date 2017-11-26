package com.startach.yedidim.modules.splashactivity;

import android.app.Activity;

import com.startach.yedidim.modules.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yb34982 on 07/11/2017.
 */
@Module
public class SplashActivityModule {
    Activity mActivity;
    public SplashActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    Activity providesActivity(){
        return mActivity;
    }
}
