package com.startach.yedidim;

import com.startach.yedidim.entities.authentication.AuthEntity;
import com.startach.yedidim.entities.authentication.AuthState;

import java.util.regex.Pattern;

import io.reactivex.Single;

/**
 * Created by yb34982 on 06/09/2017.
 */

public class LoginActivityViewModelImpl implements LoginActivityViewModel {

    final private AuthEntity authEntity;


    private static final String TAG = "LoginActivity";
    private String phoneNumber;

    public LoginActivityViewModelImpl(AuthEntity authEntity) {
        this.authEntity = authEntity;
    }

    @Override
    public boolean validNumber(CharSequence phoneNumber) {
        Pattern phonePattern = Pattern.compile("(05)(\\d{8})");
        return phonePattern.matcher(phoneNumber).matches();
    }

    @Override
    public Single<Boolean> checkIfAuthenticated() {
        return Single.just(false);
//        return authEntity.isAuthenticated();
    }

    @Override
    public Single<AuthState> verifyPhoneNumberInServer(String phoneNumber) {
        return authEntity.verifyPhoneNumber(phoneNumber);
    }

    @Override
    public Single<AuthState> verifyCodeInServer(String code) {
        return authEntity.loginWithCode(code);
    }


}
