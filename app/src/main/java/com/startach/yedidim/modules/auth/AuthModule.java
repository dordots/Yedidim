package com.startach.yedidim.modules.auth;


import android.app.Activity;

import com.startach.yedidim.entities.authentication.AuthEntity;
import com.startach.yedidim.entities.authentication.AuthEntityImpl;
import com.startach.yedidim.entities.authentication.UserRegistrationStateEntity;
import com.startach.yedidim.entities.authentication.UserRegistrationStateEntityImpl;
import com.startach.yedidim.modules.ActivityScope;
import com.startach.yedidim.network.YedidimApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthModule {

    @ActivityScope
    @Provides
    UserRegistrationStateEntity providesUserRegistrationStateEntity(YedidimApiService apiService) {
        return new UserRegistrationStateEntityImpl(apiService);
    }

    @ActivityScope
    @Provides
    AuthEntity providesAuthEntity(Activity activity, UserRegistrationStateEntity registrationStateEntity) {
        return new AuthEntityImpl(activity, registrationStateEntity);
    }
}
