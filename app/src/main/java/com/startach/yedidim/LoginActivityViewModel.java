package com.startach.yedidim;


import com.startach.yedidim.entities.authentication.AuthState;

import io.reactivex.Single;

/**
 * Created by yb34982 on 06/09/2017.
 */

public interface LoginActivityViewModel {

    boolean validNumber(CharSequence phoneNumber);

    Single<AuthState> verifyPhoneNumberInServer(String phoneNumber);

    Single<AuthState> verifyCodeInServer(String code);

    Single<AuthState> resendCode(String phoneNumber);
}
