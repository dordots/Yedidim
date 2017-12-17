package com.startach.yedidim;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.startach.yedidim.Activity.MainPageActivity;
import com.startach.yedidim.entities.authentication.AuthState;
import com.startach.yedidim.modules.App;
import com.startach.yedidim.modules.auth.AuthModule;
import com.startach.yedidim.modules.loginactivity.LoginActivityModule;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.phoneField)
    EditText m_PhoneField;

    @BindView(R.id.send_phone_button)
    Button m_SendPhoneButton;

    @BindView(R.id.codeField)
    EditText m_CodeField;

    @BindView(R.id.error_msg)
    TextView m_ErrorMsg;

    @BindView(R.id.resend_code)
    TextView m_ResendCode;


    @Inject
    LoginActivityViewModel loginActivityViewModel;

    CompositeDisposable allObsevables = new CompositeDisposable();

    Boolean phoneMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((App) getApplication()).getComponent()
                .newLoginActivitySubComponent(new LoginActivityModule(this), new AuthModule())
                .inject(this);

        setPhoneMode(phoneMode);
        showError("");

        RxTextView.textChanges(m_PhoneField).skip(1)
                .map(loginActivityViewModel::validNumber)
                .subscribe(aBoolean -> {
                    m_SendPhoneButton.setEnabled(aBoolean);
                    if (aBoolean) {
                        showError("");
                    } else
                        showError(getString(R.string.invalide_number));
                });

        RxView.clicks(m_SendPhoneButton)
                .throttleFirst(2, TimeUnit.SECONDS)
                .map(click -> phoneMode
                        ? m_PhoneField.getText().toString()
                        : m_CodeField.getText().toString()
                )
                .doOnNext(text->Timber.d(text))
                .switchMapSingle(handleSendButton())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAuthResultMapper());

        RxView.clicks(m_ResendCode)
                .throttleFirst(2, TimeUnit.SECONDS)
                .map(clicks->m_PhoneField.getText().toString())
                .switchMapSingle(handleResendButton())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAuthResultMapper());
    }

    private Function<String ,SingleSource<AuthState>> handleResendButton() {
        return phoneNum -> loginActivityViewModel.resendCode(phoneNum);
    }

    @NonNull
    private Function<String, SingleSource<? extends AuthState>> handleSendButton() {
        return numberOrCode -> {
            Timber.d("Number state : %s" , phoneMode.toString());
            return (phoneMode)
                    ? loginActivityViewModel.verifyPhoneNumberInServer(numberOrCode)
                    : loginActivityViewModel.verifyCodeInServer(numberOrCode);
        };
    }

    final Consumer<AuthState> getAuthResultMapper(){
        return results -> {
            LoginActivity.this.showResults(results);
            if (results.equals(AuthState.Success)) {
                LoginActivity.this.loadMainApplicationActivity();
            } else if (results.equals(AuthState.UnregisteredUser)) {
                LoginActivity.this.showError(LoginActivity.this.getResources().getString(R.string.unknown_phone_number));
            } else if (results.equals(AuthState.Failure)) {
                LoginActivity.this.showError(LoginActivity.this.getResources().getString(R.string.invalide_number));
            } else if (results.equals(AuthState.CodeSent)) {
                phoneMode = false;
                LoginActivity.this.setPhoneMode(phoneMode);
                LoginActivity.this.showError("");
            }
        };
    }


    private void loadMainApplicationActivity() {
        Intent intent = new Intent(this,MainPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void showResults(AuthState results) {
        Toast.makeText(this, "Results : " + results.toString(), Toast.LENGTH_SHORT).show();
    }

    private void setPhoneMode(Boolean phoneMode){
        m_CodeField.setVisibility(phoneMode ? View.GONE : View.VISIBLE);
        m_PhoneField.setVisibility(phoneMode ? View.VISIBLE : View.GONE);
        m_ResendCode.setVisibility(phoneMode ? View.GONE : View.VISIBLE);

    }

    private void showError(String errorMsg){
        m_PhoneField.setTextColor(errorMsg.isEmpty()
                ? getResources().getColor(R.color.colorPrimary)
                : getResources().getColor(R.color.error_red));
            m_ErrorMsg.setText(errorMsg);
    }


    private void doNothing() {

    }
}