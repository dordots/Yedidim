package com.startach.yedidim.modules.loginactivity;


import com.startach.yedidim.LoginActivity;
import com.startach.yedidim.modules.ActivityScope;
import com.startach.yedidim.modules.auth.AuthModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {LoginActivityModule.class, AuthModule.class})
public interface LoginActivitySubComponent {

    void inject(LoginActivity loginActivity);

}
