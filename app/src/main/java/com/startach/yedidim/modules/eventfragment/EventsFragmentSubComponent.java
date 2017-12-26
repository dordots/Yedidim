package com.startach.yedidim.modules.eventfragment;


import com.startach.yedidim.MainPageFragments.EventsFragment;
import com.startach.yedidim.modules.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {EventsFragmentModule.class})
public interface EventsFragmentSubComponent {

    void inject(EventsFragment activity);

}
