package com.startach.yedidim.modules.splashactivity;

import com.startach.yedidim.Activity.SplashScreenActivity;
import com.startach.yedidim.modules.ActivityScope;
import com.startach.yedidim.modules.auth.AuthModule;

import dagger.Subcomponent;

/**
 * Created by yb34982 on 07/11/2017.
 */
@ActivityScope
@Subcomponent(modules = {SplashActivityModule.class, AuthModule.class})
public interface SplashSubComponent {
    void inject(SplashScreenActivity activity);
}
