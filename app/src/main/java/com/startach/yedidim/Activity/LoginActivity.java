package com.startach.yedidim.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.startach.yedidim.Adapter.DeviceInfo;
import com.startach.yedidim.PlivoService.PhoneMessageTask;
import com.startach.yedidim.R;

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

        //DeviceInfo deviceInfo = new DeviceInfo(this);
        //String deviceID = deviceInfo.getIMEI();
        //deviceInfo.loadIMEI();
        //deviceInfo.doPermissionGrantedStuffs();
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

        // TODO: after paying the message service remove the toast
        Toast.makeText(this, securityCode, Toast.LENGTH_LONG).show();

        Intent verificationIntent = new Intent(this, VerifyCodeActivity.class);
        verificationIntent.putExtra("securityCode", securityCode.toString());
        verificationIntent.putExtra("phoneNumber", phoneNumber);
        startActivity(verificationIntent);
        finish();
    }
}