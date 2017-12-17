package com.startach.yedidim.modules.mainpageactivity;

import com.startach.yedidim.MainPageFragments.MainPageFragment;
import com.startach.yedidim.MainPageViewModel;
import com.startach.yedidim.MainPageViewModelImpl;
import com.startach.yedidim.entities.notification.NotificationDeviceIdSyncer;
import com.startach.yedidim.entities.usermanagement.UserManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yb34982 on 17/12/2017.
 */
@Module
public class MainPageFragmentModule {

    private final MainPageFragment mainPageFragment;

    public MainPageFragmentModule(MainPageFragment mainPageFragment) {
        this.mainPageFragment = mainPageFragment;
    }


    @Singleton
    @Provides
    MainPageViewModel provideMainPageViewModel(UserManager userManager, NotificationDeviceIdSyncer deviceIdSyncer){
        return new MainPageViewModelImpl(userManager,deviceIdSyncer);
    }

    @Singleton
    @Provides
    MainPageFragment provideFragment(){
        return mainPageFragment;
    }
}
