package com.startach.yedidim;


import com.startach.yedidim.entities.authentication.AuthState;

import io.reactivex.Observable;

/**
 * Created by yb34982 on 06/09/2017.
 */

public interface LoginActivityViewModel {

    boolean validNumber(CharSequence phoneNumber);

    Observable<AuthState> verifyPhoneNumberInServer(String phoneNumber);

    Observable<AuthState> verifyCodeInServer(String code);

    Observable<AuthState> resendCode(String phoneNumber);
}
