package com.startach.yedidim.modules.settingsfragment;

import android.app.Activity;

import com.startach.yedidim.MainPageFragments.SettingsFragment;
import com.startach.yedidim.entities.authentication.AuthEntity;
import com.startach.yedidim.modules.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yb34982 on 17/12/2017.
 */
@Module
public class SettingsFragmentModule {
    private final SettingsFragment settingsFragment;

    public SettingsFragmentModule(SettingsFragment settingsFragment) {
        this.settingsFragment = settingsFragment;
    }

    @ActivityScope
    @Provides
    SettingsFragmentViewModel providesSettingsFragmentViewModel(AuthEntity authEntity){
        return new SettingsFragmentViewModelImpl(authEntity);
    }

    @Provides
    Activity providesAvtivity(){
        return settingsFragment.getActivity();
    }


}
