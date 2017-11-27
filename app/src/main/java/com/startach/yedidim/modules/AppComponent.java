package com.startach.yedidim.modules;

import com.startach.yedidim.entities.notification.YedidimFirebaseInstanceIdService;
import com.startach.yedidim.modules.auth.AuthModule;
import com.startach.yedidim.modules.eventinfoactivity.EventInfoActivityModule;
import com.startach.yedidim.modules.eventinfoactivity.EventInfoActivitySubComponent;
import com.startach.yedidim.modules.loginactivity.LoginActivityModule;
import com.startach.yedidim.modules.loginactivity.LoginActivitySubComponent;
import com.startach.yedidim.modules.network.NetworkModule;
import com.startach.yedidim.modules.splashactivity.SplashActivityModule;
import com.startach.yedidim.modules.splashactivity.SplashSubComponent;
import com.startach.yedidim.modules.testactivity.TestActivitySubComponent;
import com.startach.yedidim.modules.testactivity.TestModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yb34982 on 26/09/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, NotificationSyncerModule.class})
public interface AppComponent {

    void inject(App app);

    LoginActivitySubComponent newLoginActivitySubComponent(LoginActivityModule activityModule, AuthModule authModule);

    TestActivitySubComponent newTestActivitySubComponent(TestModule activityModule, AuthModule authModule);

    SplashSubComponent newSplashActivitySubComponent(SplashActivityModule activityModule, AuthModule authModule);

    EventInfoActivitySubComponent newEventInfoActivitySubComponent(EventInfoActivityModule activityModule);

    void inject(YedidimFirebaseInstanceIdService yedidimFirebaseInstanceIdService);

}
