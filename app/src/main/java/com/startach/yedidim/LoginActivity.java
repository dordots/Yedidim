package com.startach.yedidim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.startach.yedidim.Activity.VerifyCodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.phoneField)
    EditText m_PhoneField;

    LoginActivityViewModel loginActivityViewModel;
    CompositeDisposable allObsevables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginActivityViewModel = new LoginActivityViewModelImpl();
        loginActivityViewModel.initRetrofit();

        RxTextView.textChanges(m_PhoneField).skip(1)
                .map(loginActivityViewModel::validNumber)
                .doOnNext(aBoolean -> {
                    if (aBoolean)
                        m_PhoneField.setError(null);
                    else
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
}