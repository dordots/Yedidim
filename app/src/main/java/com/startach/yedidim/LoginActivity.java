package com.startach.yedidim;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.startach.yedidim.PlivoService.PhoneMessageTask;

import java.security.SecureRandom;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText m_PhoneField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_PhoneField = (EditText) findViewById(R.id.phoneField);

        proccessUserInput();
    }

    private void proccessUserInput() {
        m_PhoneField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check if the user pressed 'done'
                if (actionId == EditorInfo.IME_ACTION_DONE)
                    validatePhoneNumber();
                return false;
            }
        });
    }

    private void validatePhoneNumber() {
        Pattern phonePattern = Pattern.compile("(05)(\\d{8})");

        if(phonePattern.matcher(m_PhoneField.getText()).matches()) {
            sendSecurityCode(m_PhoneField.getText().toString());
        } else {
            Toast.makeText(this, "מס' פלאפון לא תקין!", Toast.LENGTH_LONG).show();
        }
    }

    private void sendSecurityCode(String phoneNumber) {
        SecureRandom randomNumber = new SecureRandom();
        StringBuilder securityCode = new StringBuilder();

        for (int index = 0; index < 6; index++)
            securityCode.append(randomNumber.nextInt(10));

        new PhoneMessageTask().execute(phoneNumber, "קוד האימות שלך הוא " + securityCode + ".");

        Intent verificationIntent = new Intent(this, VerifyCodeActivity.class);
        verificationIntent.putExtra("securityCode", securityCode.toString());
        startActivity(verificationIntent);
    }
}