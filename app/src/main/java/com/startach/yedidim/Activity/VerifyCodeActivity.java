package com.startach.yedidim.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.startach.yedidim.PlivoService.PhoneMessageTask;
import com.startach.yedidim.R;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by yuval on 25/07/2017.
 */

public class VerifyCodeActivity extends AppCompatActivity {
    private EditText _verifyCode;
    private Button _checkCodeButton;
    private String _securityCode;
    private TextView _sendCodeAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        _verifyCode = (EditText) findViewById(R.id.verify_password);
        _checkCodeButton = (Button) findViewById(R.id.check_password);
        _securityCode = getSecurityCodeFromIntent();
        _sendCodeAgain = (TextView) findViewById(R.id.sendAgainVerifyCode);

        _sendCodeAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = getPhoneNumberFromIntent();
                new PhoneMessageTask().execute(phoneNumber, "קוד האימות שלך הוא " + _securityCode + ".");
                Toast.makeText(getApplicationContext(), _securityCode, LENGTH_LONG).show();
            }
        });

        _checkCodeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (userDidntEnterInput()) {
                    Toast.makeText(getApplicationContext(), "אנא הכנס קוד אישור", LENGTH_LONG).show();
                } else {
                    validateCode();
                }
            }
        });
    }

    private String getSecurityCodeFromIntent() {
        Intent loginIntent = getIntent();
        return loginIntent.getStringExtra("securityCode");
    }

    private String getPhoneNumberFromIntent() {
        Intent loginIntent = getIntent();
        return loginIntent.getStringExtra("phoneNumber");
    }

    private boolean userDidntEnterInput() {
        return _verifyCode.getText().toString().equals("");
    }

    private void validateCode() {
        if (codeIsCorrect()) {
            Toast.makeText(getApplicationContext(), "הקוד הוזן בהצלחה", Toast.LENGTH_SHORT).show();

            updateUserFirstPage();

            Intent homeIntent = new Intent(getApplicationContext(), PersonalInformationActivity.class);
            startActivity(homeIntent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "הקוד שגוי", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean codeIsCorrect() {
        return _verifyCode.getText().toString().equals(_securityCode);
    }

    private void updateUserFirstPage() {
        // Todo(1): check if the user's phone exists in the server
        //  if it does --> generate new id for the user and update the id-phone table
        // else --> generate unique id for the user and create new row in the id-phone table
        // Todo(2): save the user's generated id in his shared preferences file.
        // Todo(3): update the user's first page when he opens the app in the database

        // For now - save the user's first page in his shared preferences file.
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.first_page_variable), String.valueOf(R.string.personal_information_id));
        editor.commit();
    }
}