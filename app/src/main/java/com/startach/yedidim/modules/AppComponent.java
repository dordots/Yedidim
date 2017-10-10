package com.startach.yedidim.modules;

import com.startach.yedidim.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yb34982 on 26/09/2017.
 */

@Singleton
@Component(modules = {AppModule.class,LoginActivityModule.class})
public interface AppComponent {

    void inject(App app);
    void inject(LoginActivity loginActivity);
}
