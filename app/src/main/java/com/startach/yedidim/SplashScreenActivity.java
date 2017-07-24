package com.startach.yedidim;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {
    private CountDownTimer splashTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashTimer = new CountDownTimer(5000, 5000) {
            public void onTick(long milis) {
            }

            public void onFinish() {
                enterLoginScreen();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        splashTimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("Test", "Pause");
        splashTimer.cancel();
    }

    private void enterLoginScreen() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
