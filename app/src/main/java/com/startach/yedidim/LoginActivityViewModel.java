package com.startach.yedidim;

import android.widget.EditText;

import io.reactivex.Observable;

/**
 * Created by yb34982 on 06/09/2017.
 */

public interface LoginActivityViewModel {

    boolean validNumber(CharSequence phoneNumber);

    Observable<Boolean> getEditActionDoneObservable(EditText m_phoneField);
}
