package com.startach.yedidim.modules.eventinfoactivity;


import com.startach.yedidim.EventInfoActivity;
import com.startach.yedidim.modules.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {EventInfoActivityModule.class})
public interface EventInfoActivitySubComponent {

    void inject(EventInfoActivity activity);

}
