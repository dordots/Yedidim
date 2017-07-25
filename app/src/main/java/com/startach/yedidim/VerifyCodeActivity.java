package com.startach.yedidim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by yuval on 25/07/2017.
 */

public class VerifyCodeActivity extends AppCompatActivity {
//    private final String CODE = "1234";
    private EditText mVerifyCode;
    private Button mCheckCode;
    private String firstCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        mVerifyCode = (EditText) findViewById(R.id.verify_password);
        mCheckCode = (Button) findViewById(R.id.check_password);

        Intent loginIntent = getIntent();
        firstCode = loginIntent.getStringExtra("code");

        mCheckCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mVerifyCode.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "אנא הכנס קוד אישור", Toast.LENGTH_LONG).show();
                } else {
                    if (mVerifyCode.getText().toString().equals(firstCode)) {
                        Toast.makeText(getApplicationContext(), "הקוד הוזן בהצלחה", Toast.LENGTH_LONG).show();
                        Intent homeIntent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                        startActivity(homeIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "הקוד שגוי", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
