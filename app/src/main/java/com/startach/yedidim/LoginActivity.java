package com.startach.yedidim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.startach.yedidim.PlivoService.PhoneMessageTask;

import java.security.SecureRandom;

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
                        sendSecurityCode(m_PhoneField.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.invalide_phone_number, Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void sendSecurityCode(String phoneNumber) {
        SecureRandom randomNumber = new SecureRandom();
        StringBuilder securityCode = new StringBuilder();

        for (int index = 0; index < 6; index++)
            securityCode.append(randomNumber.nextInt(10));

        new PhoneMessageTask().execute(phoneNumber, "קוד האימות שלך הוא " + securityCode + ".");

        // TODO: after paying the message service remove the toast
        Toast.makeText(this, securityCode, Toast.LENGTH_LONG).show();

        Intent verificationIntent = new Intent(this, VerifyCodeActivity.class);
        verificationIntent.putExtra("securityCode", securityCode.toString());
        verificationIntent.putExtra("phoneNumber", phoneNumber);
        startActivity(verificationIntent);
        finish();
    }
}