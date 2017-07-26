package com.startach.yedidim;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by gahal on 26/07/2017.
 */

public class SmsListener extends BroadcastReceiver {
    public static final String SMS_EXTRA_NAME = "pdus";

    @Override
    public void onReceive( Context context, Intent intent )
    {
        context.sendBroadcast(intent);
    }
}
