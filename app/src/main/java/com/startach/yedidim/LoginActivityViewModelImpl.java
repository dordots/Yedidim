package com.startach.yedidim;

import com.startach.yedidim.entities.authentication.AuthEntity;
import com.startach.yedidim.entities.authentication.AuthState;
import com.startach.yedidim.entities.notification.NotificationDeviceIdSyncer;

import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Created by yb34982 on 06/09/2017.
 */

public class LoginActivityViewModelImpl implements LoginActivityViewModel {

    private static final String TAG = "LoginActivity";

    private final AuthEntity authEntity;
    private final NotificationDeviceIdSyncer notificationDeviceIdSyncer;

    public LoginActivityViewModelImpl(AuthEntity authEntity, NotificationDeviceIdSyncer notificationDeviceIdSyncer) {
        this.authEntity = authEntity;
        this.notificationDeviceIdSyncer = notificationDeviceIdSyncer;
    }

    @Override
    public boolean validNumber(CharSequence phoneNumber) {
        Pattern phonePattern = Pattern.compile("(05)(\\d{8})");
        return phonePattern.matcher(phoneNumber).matches();
    }

    private ObservableTransformer<AuthState, AuthState> deviceIdSyncTransformer = new ObservableTransformer<AuthState, AuthState>() {

        @Override
        public ObservableSource<AuthState> apply(Observable<AuthState> upstream) {
            return upstream
                    .flatMap(authState -> {
                        if (authState == AuthState.Success) {
                            return notificationDeviceIdSyncer.syncDeviceID()
                                    .andThen(Observable.just(AuthState.Success));
                        } else {
                            return Observable.just(authState);
                        }
                    });
        }
    };

    @Override
    public Observable<AuthState> verifyPhoneNumberInServer(String phoneNumber) {
        return authEntity.verifyPhoneNumber(phoneNumber)
                .compose(deviceIdSyncTransformer);
    }

    @Override
    public Observable<AuthState> verifyCodeInServer(String code) {
        return authEntity.loginWithCode(code)
                .compose(deviceIdSyncTransformer);
    }

    @Override
    public Observable<AuthState> resendCode(String phoneNumber) {
        return authEntity.verificationRetry(phoneNumber)
                .compose(deviceIdSyncTransformer);
    }


}
