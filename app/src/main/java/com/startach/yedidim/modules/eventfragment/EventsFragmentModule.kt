package com.startach.yedidim.modules.eventfragment

import com.startach.yedidim.MainPageFragments.EventsFragment
import com.startach.yedidim.entities.usermanagement.UserManager
import com.startach.yedidim.modules.ActivityScope
import com.startach.yedidim.network.EventApi
import com.startach.yedidim.network.YedidimApiService
import com.startach.yedidim.network.YedidimCloudFunctionsApiService
import dagger.Module
import dagger.Provides

@Module
class EventsFragmentModule(private val fragment: EventsFragment) {


    @ActivityScope
    @Provides
    internal fun providesEventApi(userManager: UserManager, yedidimCloudFunctionsApiService: YedidimCloudFunctionsApiService, yedidimApiService: YedidimApiService): EventApi {
        return EventApi(userManager, yedidimCloudFunctionsApiService, yedidimApiService)
    }

    @Provides
    internal fun providesActivity(): EventsFragment {
        return fragment
    }
}