package com.startach.yedidim.modules.mainpageactivity;

import com.startach.yedidim.MainPageFragments.MainPageFragment;
import com.startach.yedidim.modules.NotificationSyncerModule;

import javax.inject.Singleton;

import dagger.Subcomponent;

/**
 * Created by yb34982 on 17/12/2017.
 */
@Singleton
@Subcomponent(modules = {MainPageFragmentModule.class, NotificationSyncerModule.class})
public interface MainPageFragmentSubComponent {
    void inject(MainPageFragment mainPageFragment);
}
