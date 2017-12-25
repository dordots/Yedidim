package com.startach.yedidim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.startach.yedidim.LoginActivity;
import com.startach.yedidim.R;
import com.startach.yedidim.entities.authentication.AuthEntity;
import com.startach.yedidim.modules.App;
import com.startach.yedidim.modules.auth.AuthModule;
import com.startach.yedidim.modules.splashactivity.SplashActivityModule;
import com.startach.yedidim.testfairy.TestFairyInit;

import javax.inject.Inject;

public class SplashScreenActivity extends AppCompatActivity {

    @Inject
    AuthEntity authEntity;

    private ImageView m_Logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TestFairyInit.start(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ((App)getApplication()).getComponent()
                .newSplashActivitySubComponent(new SplashActivityModule(this),new AuthModule())
                .inject(this);

        m_Logo = (ImageView) findViewById(R.id.logo);
        m_Logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));

        navigateUserToFirstPage();

    }

    private void navigateUserToFirstPage() {
           authEntity.isAuthenticated()
                    .subscribe(
                            aBoolean -> goToActivity(aBoolean ? MainPageActivity.class : LoginActivity.class)
                    );
    }

    private void goToActivity(Class activityClass) {
        Intent loginIntent = new Intent(this, activityClass);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
        finish();
    }
}