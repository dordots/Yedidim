package com.startach.yedidim.modules.testactivity;

import com.startach.yedidim.TestActivity;
import com.startach.yedidim.modules.ActivityScope;
import com.startach.yedidim.modules.auth.AuthModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {TestModule.class, AuthModule.class})
public interface TestActivitySubComponent {

    void inject(TestActivity loginActivity);

}
