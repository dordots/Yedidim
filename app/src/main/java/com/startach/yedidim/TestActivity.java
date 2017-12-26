package com.startach.yedidim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.startach.yedidim.entities.authentication.AuthEntity;
import com.startach.yedidim.entities.authentication.AuthState;
import com.startach.yedidim.entities.notification.NotificationDeviceIdSyncer;
import com.startach.yedidim.entities.usermanagement.VolunteerLocationUpdater;
import com.startach.yedidim.modules.App;
import com.startach.yedidim.modules.auth.AuthModule;
import com.startach.yedidim.modules.testactivity.TestModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Retrofit;
import timber.log.Timber;

public class TestActivity extends AppCompatActivity {

    @Inject Retrofit retrofit;
    @Inject AuthEntity authEntity;
    @Inject NotificationDeviceIdSyncer deviceIdSyncer;
    @Inject VolunteerLocationUpdater volunteerLocationUpdater;

    @BindView(R.id.editText2) EditText codeEditText;
    @BindView(R.id.editTextPhone) EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().newTestActivitySubComponent(new TestModule(this), new AuthModule())
                .inject(this);
    }

    @OnClick(R.id.button2)
    public void sendSmsClicked() {
        authEntity.isAuthenticated()
                .flatMapObservable(isAuthenticated -> {
                    Timber.d("isAuthenticated = " + isAuthenticated);
                    if (isAuthenticated) {
                        return Observable.just(AuthState.Success);
                    } else {
                        return authEntity.verifyPhoneNumber(editTextPhone.getText().toString())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(authState -> Timber.d("sendSmsClicked autState = " + authState));
    }

    @OnClick(R.id.button3)
    public void verifyClicked() {
        authEntity.loginWithCode(codeEditText.getText().toString())
                .subscribe(authState -> Timber.d("verifyClicked autState = " + authState));
    }

    @OnClick(R.id.buttonLogOut)
    public void logout() {
        authEntity.logout();
    }

    @OnClick(R.id.btnTest)
    public void btnTest() {

    }

    @OnClick(R.id.btnDeviceId)
    public void btnDeviceId() {
        deviceIdSyncer.syncDeviceID()
                .subscribe(() -> Timber.d("syncDeviceID success!"),
                        throwable -> Timber.d(throwable, "syncDeviceID failed!"));
    }

    @OnClick(R.id.btnUpdateLocation)
    public void btnUpdateLocation() {
        volunteerLocationUpdater.updateLocation(33.4, 23.4)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("update location succeed!"), throwable -> Timber.d(throwable, "update location failed!"));
    }
}
