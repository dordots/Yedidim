package com.startach.yedidim.modules.eventinfoactivity

import android.app.Activity
import com.startach.yedidim.EventInfoActivity
import com.startach.yedidim.EventInfoViewModel
import com.startach.yedidim.EventInfoViewModelImpl
import com.startach.yedidim.entities.usermanagement.UserManager
import com.startach.yedidim.modules.ActivityScope
import com.startach.yedidim.network.EventApi
import com.startach.yedidim.network.YedidimApiService
import com.startach.yedidim.network.YedidimCloudFunctionsApiService
import dagger.Module
import dagger.Provides

@Module
class EventInfoActivityModule(private val activity: EventInfoActivity) {


    @ActivityScope
    @Provides
    internal fun providesEventApi(userManager: UserManager, yedidimCloudFunctionsApiService: YedidimCloudFunctionsApiService, yedidimApiService: YedidimApiService): EventApi {
        return EventApi(userManager, yedidimCloudFunctionsApiService, yedidimApiService)
    }

    @ActivityScope
    @Provides
    internal fun provideEventInfoActivityViewModel(eventApi: EventApi): EventInfoViewModel {
        return EventInfoViewModelImpl(eventApi)
    }

    @Provides
    internal fun providesActivity(): Activity {
        return activity
    }
}