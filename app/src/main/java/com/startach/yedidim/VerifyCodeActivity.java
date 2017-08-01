package com.startach.yedidim;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by yuval on 25/07/2017.
 */

public class VerifyCodeActivity extends AppCompatActivity {
    private EditText _verifyCode;
    private Button _checkCodeButton;
    private String _securityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        _verifyCode = (EditText) findViewById(R.id.verify_password);
        _checkCodeButton = (Button) findViewById(R.id.check_password);
        _securityCode = getSecurityCode();

        _checkCodeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (_verifyCode.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "אנא הכנס קוד אישור", Toast.LENGTH_LONG).show();
                } else {
                    validateCode();
                }
            }
        });
    }

    private String getSecurityCode() {
        Intent loginIntent = getIntent();
        return loginIntent.getStringExtra("securityCode");
    }
    public void validateCode() {
        if (_verifyCode.getText().toString().equals(_securityCode)) {
            Toast.makeText(getApplicationContext(), "הקוד הוזן בהצלחה", Toast.LENGTH_SHORT).show();

            saveCommittedFirstLogin();

            Intent homeIntent = new Intent(getApplicationContext(), PersonalInformationActivity.class);
            startActivity(homeIntent);
        } else {
            Toast.makeText(getApplicationContext(), "הקוד שגוי", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveCommittedFirstLogin() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.first_page_variable), String.valueOf(R.string.personal_information_id));
        editor.commit();
    }
}
