package com.startach.yedidim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.startach.yedidim.Activity.MainPageActivity;
import com.startach.yedidim.entities.AuthState;
import com.startach.yedidim.modules.App;
import com.startach.yedidim.modules.login.AuthModule;
import com.startach.yedidim.modules.login.LoginActivityModule;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.phoneField)
    EditText m_PhoneField;

    @BindView(R.id.send_sms_button)
    Button m_SendSMSButton;

    @BindView(R.id.codeField)
    EditText m_CodeField;

    @BindView(R.id.send_code_button)
    Button m_SendCodeButton;

    @BindView(R.id.code_field_layout)
    LinearLayout codeFieldLayout;


    @Inject
    LoginActivityViewModel loginActivityViewModel;

    CompositeDisposable allObsevables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((App) getApplication()).getComponent()
                .newLoginActivitySubComponent(new LoginActivityModule(this), new AuthModule())
                .inject(this);

        m_PhoneField.setVisibility(View.INVISIBLE);
        m_SendSMSButton.setEnabled(false);
        codeFieldLayout.setVisibility(View.INVISIBLE);

        loginActivityViewModel.checkIfAuthenticated()
                .subscribe(aBoolean -> {
                    Timber.d("Auth : %s",aBoolean);
                    m_PhoneField.setVisibility(aBoolean ? View.INVISIBLE : View.VISIBLE);
                    if (aBoolean){
                        loadMainApplicationActivity();
                    }
                });

        RxTextView.textChanges(m_PhoneField).skip(1)
                .map(loginActivityViewModel::validNumber)
                .subscribe(aBoolean -> {
                    m_SendSMSButton.setEnabled(aBoolean);
                    if (aBoolean) {
                        m_PhoneField.setError(null);
                    } else
                        m_PhoneField.setError(getString(R.string.invalide_phone_number));
                });

        RxView.clicks(m_SendSMSButton)
                .throttleFirst(5, TimeUnit.SECONDS)
                .map(click -> m_PhoneField.getText().toString())
                .map(number -> loginActivityViewModel.verifyPhoneNumberInServer(number)
                        .subscribe(authResultMapper))
                .subscribe()
        ;

        RxView.clicks(m_SendCodeButton)
                .throttleFirst(5, TimeUnit.SECONDS)
                .map(click -> m_CodeField.getText().toString())
                .doOnNext(code -> {
                    loginActivityViewModel.verifyCodeInServer(code)
                            .subscribe(authResultMapper);
                })
                .subscribe();



    }

    final Consumer<AuthState> authResultMapper =
            (results) -> {
                showResults(results);
                if (results.equals(AuthState.Success)) {
                    loadMainApplicationActivity();
                } else if (results.equals(AuthState.Failure)) {

                } else if (results.equals(AuthState.CodeSent)) {
                    codeFieldLayout.setVisibility(View.VISIBLE);
                }
            };

    private void showResults(AuthState results) {
        Toast.makeText(this,"Results : " + results.toString(), Toast.LENGTH_SHORT).show();
    }
//     authEntity.verifyPhoneNumber(m_PhoneField.getText().toString())
//            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(authState -> Toast.makeText(getApplicationContext(), authState.toString(), Toast.LENGTH_SHORT).show());


    private void loadMainApplicationActivity() {
        final Intent mainPage = new Intent(getApplicationContext(), MainPageActivity.class);
        startActivity(mainPage);
    }


    private void doNothing() {

    }
}