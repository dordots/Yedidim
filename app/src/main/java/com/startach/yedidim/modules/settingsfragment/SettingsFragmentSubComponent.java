package com.startach.yedidim.modules.settingsfragment;

import com.startach.yedidim.MainPageFragments.SettingsFragment;
import com.startach.yedidim.modules.ActivityScope;
import com.startach.yedidim.modules.auth.AuthModule;

import dagger.Subcomponent;

/**
 * Created by yb34982 on 17/12/2017.
 */
@ActivityScope
@Subcomponent(modules = {SettingsFragmentModule.class, AuthModule.class})
public interface SettingsFragmentSubComponent {
    void inject(SettingsFragment settingsFragment);
}
