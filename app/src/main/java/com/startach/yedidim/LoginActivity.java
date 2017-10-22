package com.startach.yedidim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.startach.yedidim.Activity.VerifyCodeActivity;
import com.startach.yedidim.entities.AuthEntity;
import com.startach.yedidim.modules.App;
import com.startach.yedidim.modules.login.AuthModule;
import com.startach.yedidim.modules.login.LoginActivityModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.phoneField)
    EditText m_PhoneField;

    @Inject
    LoginActivityViewModel loginActivityViewModel;
    @Inject
    AuthEntity authEntity;

    CompositeDisposable allObsevables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((App) getApplication()).getComponent()
                .newLoginActivitySubComponent(new LoginActivityModule(this), new AuthModule())
                .inject(this);

        loginActivityViewModel.initRetrofit();

        RxTextView.textChanges(m_PhoneField).skip(1)
                .map(loginActivityViewModel::validNumber)
                .doOnNext(aBoolean -> {
                    if (aBoolean) {
                        m_PhoneField.setError(null);
                        authEntity.verifyPhoneNumber(m_PhoneField.getText().toString())
                                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(authState -> Toast.makeText(getApplicationContext(), authState.toString(), Toast.LENGTH_SHORT).show());
                    } else
                        m_PhoneField.setError(getString(R.string.invalide_phone_number));
                })
                .subscribe();

        loginActivityViewModel.getEditActionDoneObservable(m_PhoneField)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        loginActivityViewModel.requestSMS()
                                .subscribe(
                                        (responseCode) -> smsCodeAccepted(responseCode.first, responseCode.second),
                                        (throwable) -> Toast.makeText(getApplicationContext(), R.string.server_access_error, Toast.LENGTH_LONG).show());
                    } else {
                        // TODO: after paying the message service remove the toast
                        Toast.makeText(getApplicationContext(), R.string.invalide_phone_number, Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void smsCodeAccepted(String phoneNumber, String securityCode) {
        Toast.makeText(this, securityCode, Toast.LENGTH_LONG).show();

        Intent verificationIntent = new Intent(this, VerifyCodeActivity.class);
        verificationIntent.putExtra("securityCode", securityCode);
        verificationIntent.putExtra("phoneNumber", phoneNumber);
        startActivity(verificationIntent);
        finish();
    }

    private void doNothing() {

    }
}