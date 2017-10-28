package com.startach.yedidim.modules.login;


import com.startach.yedidim.LoginActivity;

import dagger.Subcomponent;

@LoginActivityScope
@Subcomponent(modules = {LoginActivityModule.class, AuthModule.class})
public interface LoginActivitySubComponent {

    void inject(LoginActivity loginActivity);

}
