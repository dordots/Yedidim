package com.startach.yedidim;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

/**
 * Created by gahal on 31/07/2017.
 */

public class PhoneMessageTask extends AsyncTask<String, Void, Void> {
    private static String AUTH_ID = "MANZI5MTG5MDNJOWM5OT";
    private static String AUTH_TOKEN = "ZTY3YjZjODk4ZGM5MzU2NmZiYTRkNTcyNDM1OTY4";

    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection client = null;

        try {
            URL url = new URL("https://api.plivo.com/v1/Account/" + AUTH_ID + "/Message/");
            client = (HttpURLConnection) url.openConnection();

            Authenticator.setDefault (new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication (AUTH_ID, AUTH_TOKEN.toCharArray());
                }
            });

            client.setRequestMethod("POST");
            client.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            client.setDoOutput(true);

            String phoneNumber = "972" + params[0].substring(1);
            String message = params[1];
            String json = "{\"src\":\"Yedidim\",\"dst\":\""+ phoneNumber +"\", \"text\":\"" + message + "\"}";

            DataOutputStream wr = new DataOutputStream(client.getOutputStream());
            wr.write(json.getBytes("UTF-8"));
            wr.flush();
            wr.close();

            java.util.Scanner s = new java.util.Scanner(client.getErrorStream()).useDelimiter("\\A");
            Log.d("sms", s.hasNext() ? s.next() : "");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
