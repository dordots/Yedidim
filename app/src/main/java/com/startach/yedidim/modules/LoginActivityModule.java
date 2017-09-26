package com.startach.yedidim.modules;

import com.startach.yedidim.LoginActivityViewModel;
import com.startach.yedidim.LoginActivityViewModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yb34982 on 26/09/2017.
 */
@Module
public class LoginActivityModule {

    @Provides
    LoginActivityViewModel provideLoginActivityViewModel(){
        return new LoginActivityViewModelImpl();
    }
}
