package com.startach.yedidim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {
    private CountDownTimer m_SplashTimer = null;
    private ImageView m_Logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        m_Logo = (ImageView) findViewById(R.id.logo);
        m_Logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));

        if (m_SplashTimer == null) {
            m_SplashTimer = new CountDownTimer(2500, 100) {
                public void onTick(long milis) {
                }

                public void onFinish() {
                    moveToNextScreen();
                }
            };
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        m_SplashTimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        m_SplashTimer.cancel();
    }

    private void moveToNextScreen() {
        if (checkIfFirstLogin().equals(String.valueOf(R.string.personal_information_id))) {
            Intent personalInformation = new Intent(getApplicationContext(), PersonalInformationActivity.class);
            startActivity(personalInformation);
        } else if(checkIfFirstLogin().equals(String.valueOf(R.string.main_page_id))) {
            Intent mainPage = new Intent(getApplicationContext(), MainPageActivity.class);
            startActivity(mainPage);
        }else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    private String checkIfFirstLogin() {
        return "true";
    }
}