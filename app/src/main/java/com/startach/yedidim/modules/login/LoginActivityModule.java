package com.startach.yedidim.modules.login;

import android.app.Activity;

import com.startach.yedidim.LoginActivity;
import com.startach.yedidim.LoginActivityViewModel;
import com.startach.yedidim.LoginActivityViewModelImpl;

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

    @LoginActivityScope
    @Provides
    LoginActivityViewModel provideLoginActivityViewModel() {
        return new LoginActivityViewModelImpl();
    }

    @Provides
    Activity providesActivity() {
        return activity;
    }
}
