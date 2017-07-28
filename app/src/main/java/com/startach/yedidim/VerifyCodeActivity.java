package com.startach.yedidim;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    private EditText mVerifyCode;
    private Button mCheckCode;
    private String firstCode;

    private BroadcastReceiver smsReciever;

    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        ActivityCompat.requestPermissions(this,
                                          new String[]{Manifest.permission.RECEIVE_SMS},
                                          MY_PERMISSIONS_REQUEST_SMS_RECEIVE);

        smsReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
                    StringBuilder totalMessage = new StringBuilder();

                    for (SmsMessage message : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                        totalMessage.append(message.getMessageBody());
                    }

                    Toast.makeText(context, totalMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };

        registerReceiver(smsReciever, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));

        mVerifyCode = (EditText) findViewById(R.id.verify_password);
        mCheckCode = (Button) findViewById(R.id.check_password);

        Intent loginIntent = getIntent();
        firstCode = loginIntent.getStringExtra("securityCode");

        mCheckCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mVerifyCode.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "אנא הכנס קוד אישור", Toast.LENGTH_LONG).show();
                } else {
                    validateCode(firstCode);
                }
            }
        });
    }

    public void validateCode(String code) {
        if (mVerifyCode.getText().toString().equals(code)) {
            Toast.makeText(getApplicationContext(), "הקוד הוזן בהצלחה", Toast.LENGTH_SHORT).show();
            Intent homeIntent = new Intent(getApplicationContext(), PersonalInformationActivity.class);
            startActivity(homeIntent);
        } else {
            Toast.makeText(getApplicationContext(), "הקוד שגוי", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReciever);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SMS_RECEIVE) {
            // YES!!
            Log.i("TAG", "MY_PERMISSIONS_REQUEST_SMS_RECEIVE --> YES");
        }
    }
}
