package com.startach.yedidim.modules.auth;


import android.app.Activity;

import com.startach.yedidim.entities.authentication.AuthEntity;
import com.startach.yedidim.entities.authentication.AuthEntityImpl;
import com.startach.yedidim.entities.authentication.UserRegistrationStateEntity;
import com.startach.yedidim.entities.authentication.UserRegistrationStateEntityImpl;
import com.startach.yedidim.modules.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @ActivityScope
    @Provides
    UserRegistrationStateEntity providesUserRegistrationStateEntity(Retrofit retrofit) {
        return new UserRegistrationStateEntityImpl(retrofit);
    }

    @ActivityScope
    @Provides
    AuthEntity providesAuthEntity(Activity activity, UserRegistrationStateEntity registrationStateEntity) {
        return new AuthEntityImpl(activity, registrationStateEntity);
    }
}
