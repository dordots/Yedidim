package com.startach.yedidim.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.startach.yedidim.LoginActivity;
import com.startach.yedidim.R;

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
                    navigateUserToFirstPage();
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

    private void navigateUserToFirstPage() {
        // Todo: check if the user has saved id in the shared preferences file.
        // if it does - navigate him to his first page.
        // else - navigate him to the login page.

        // for now - navigate the user by the data in the shared preferences file.
        if (getUserFirstPage().equals(String.valueOf(R.string.personal_information_id))) {
            Intent personalInformation = new Intent(getApplicationContext(), PersonalInformationActivity.class);
            startActivity(personalInformation);
        } else if(getUserFirstPage().equals(String.valueOf(R.string.main_page_id))) {
            Intent mainPage = new Intent(getApplicationContext(), MainPageActivity.class);
            startActivity(mainPage);
        }else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
        finish();
    }

    private String getUserFirstPage() {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return prefs.getString(String.valueOf(R.string.first_page_variable), "login_page");
    }
}