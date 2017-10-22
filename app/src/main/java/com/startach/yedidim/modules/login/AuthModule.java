package com.startach.yedidim.modules.login;


import android.app.Activity;

import com.startach.yedidim.entities.AuthEntity;
import com.startach.yedidim.entities.AuthEntityImpl;
import com.startach.yedidim.entities.UserRegistrationStateEntity;
import com.startach.yedidim.entities.UserRegistrationStateEntityImpl;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @LoginActivityScope
    @Provides
    UserRegistrationStateEntity providesUserRegistrationStateEntity(Retrofit retrofit) {
        return new UserRegistrationStateEntityImpl(retrofit);
    }

    @LoginActivityScope
    @Provides
    AuthEntity providesAuthEntity(Activity activity, UserRegistrationStateEntity registrationStateEntity) {
        return new AuthEntityImpl(activity, registrationStateEntity);
    }
}
