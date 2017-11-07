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

import javax.inject.Inject;

public class SplashScreenActivity extends AppCompatActivity {

    @Inject
    AuthEntity authEntity;

    private ImageView m_Logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ((App)getApplication()).getComponent()
                .newSplashActivitySubComponent(new SplashActivityModule(this),new AuthModule())
                .inject(this);

        m_Logo = (ImageView) findViewById(R.id.logo);
        m_Logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));

        navigateUserToFirstPage();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void navigateUserToFirstPage() {
        // Todo: check if the user has saved id in the shared preferences file.
        // if it does - navigate him to his first page.
        // else - navigate him to the login page.

            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);

    }


}