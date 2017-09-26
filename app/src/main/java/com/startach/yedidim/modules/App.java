package com.startach.yedidim.modules;

import android.app.Application;

/**
 * Created by yb34982 on 26/09/2017.
 */

public class App extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .loginActivityModule(new LoginActivityModule())
                .build();
    }

    public AppComponent getComponent(){
        return component;
    }
}
