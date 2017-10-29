package com.startach.yedidim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.startach.yedidim.LoginActivity;
import com.startach.yedidim.R;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView m_Logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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