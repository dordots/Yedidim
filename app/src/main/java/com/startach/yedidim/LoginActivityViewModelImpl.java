package com.startach.yedidim;

import android.util.Log;
import android.util.Pair;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.startach.yedidim.entities.SMSRequestResponse;
import com.startach.yedidim.entities.SMSRequestResponseStatus;
import com.startach.yedidim.network.RetrofitProvider;
import com.startach.yedidim.network.YedidimRetrofitService;

import java.security.SecureRandom;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yb34982 on 06/09/2017.
 */

class LoginActivityViewModelImpl implements LoginActivityViewModel {

    private static final String TAG = "LoginActivity";
    private YedidimRetrofitService yedidimRetrofitService;
    private String phoneNumber;

    @Override
    public void initRetrofit() {
        yedidimRetrofitService = RetrofitProvider.newRetrofitInstance().create(YedidimRetrofitService.class);
    }

    @Override
    public boolean validNumber(CharSequence phoneNumber) {
        Pattern phonePattern = Pattern.compile("(05)(\\d{8})");
        return phonePattern.matcher(phoneNumber).matches();
    }

    @Override
    public Observable<Boolean> getEditActionDoneObservable(EditText m_phoneField) {
        return RxTextView.editorActionEvents(m_phoneField)
                .filter(textViewEditorActionEvent -> textViewEditorActionEvent.actionId() == EditorInfo.IME_ACTION_DONE)
                .map(textViewEditorActionEvent -> textViewEditorActionEvent.view().getText().toString())
                .map((phoneNumber1) -> {
                    Boolean valid = validNumber(phoneNumber1);
                    if (valid)
                        phoneNumber = phoneNumber1;
                    return valid;
                });
    }

    @Override
    public Observable<Pair<String, String>> requestSMS() {
//        return getSmsRequestResponseObservable() // Todo : will beReplace when there will be a real server
        return getMockSmsRequestResponseObservable()
                .doOnSubscribe(disposable -> Log.d(TAG,"OnSubscribe"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(smsRequestResponse -> Pair.create(phoneNumber,smsRequestResponse.getReceivedCode()));
    }

    private Observable<SMSRequestResponse> getSmsRequestResponseObservable() {
        return yedidimRetrofitService.sendLoginSMS(phoneNumber);
    }


    private Observable<SMSRequestResponse> getMockSmsRequestResponseObservable() {
        StringBuilder securityCode = new StringBuilder();
        SecureRandom randomNumber = new SecureRandom();
        for (int index = 0; index < 6; index++)
            securityCode.append(randomNumber.nextInt(10));
        return Observable.just(securityCode.toString())
                .map(code -> new SMSRequestResponse(SMSRequestResponseStatus.Success,code));
    }
}
