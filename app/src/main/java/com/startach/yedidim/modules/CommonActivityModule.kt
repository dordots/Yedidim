package com.startach.yedidim.modules

import android.app.Activity
import com.startach.yedidim.Navigator
import dagger.Module
import dagger.Provides

@Module
class CommonActivityModule {

    @Provides
    @ActivityScope
    internal fun providesNavigator(activity: Activity): Navigator {
        return Navigator(activity)
    }
}