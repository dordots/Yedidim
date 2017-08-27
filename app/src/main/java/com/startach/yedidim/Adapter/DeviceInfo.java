package com.startach.yedidim.Adapter;

/**
 * Created by user on 27-Aug-17.
 */
 
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.startach.yedidim.R;


/**
 * Created by damra on 15/04/17.
 */

public class DeviceInfo extends AppCompatActivity {

    final private static int PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;

    final private Activity activity;

    public DeviceInfo(Activity activity) {
        this.activity = activity;
    }

    /**
     * Called when the 'loadIMEI' function is triggered.
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already been granted.
            doPermissionGrantedStuffs();
        }
    }


    /**
     * Requests the READ_PHONE_STATE permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.

            alertPerm(getString(R.string.permission_read_phone_state), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            PERMISSIONS_REQUEST_READ_PHONE_STATE);
                    doPermissionGrantedStuffs();

                }
            });

        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, proceed with displaying IMEI Number
                doPermissionGrantedStuffs();

            } else {

                alertPerm(getString(R.string.permissions_not_granted_read_phone), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doPermissionGrantedStuffs();
                    }
                });

            }
        }
    }

    private void alertPerm(String msg,DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setTitle("Permission Request")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, okListener)
                .setIcon(R.mipmap.ic_launcher_round)
                .show();
    }


    public String getIMEI(){
        //Have an  object of TelephonyManager
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

        //Get IMEI Number of Phone
        return tm.getDeviceId();
    }

    public void doPermissionGrantedStuffs() {

        //Have an  object of TelephonyManager
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

        //Get IMEI Number of Phone
        String IMEINumber = tm.getDeviceId();

        //Get Subscriber ID
        String subscriberID = tm.getSubscriberId();

        //Get SIM Serial Number
        String SIMSerialNumber = tm.getSimSerialNumber();

        //Get Line Number
        String lineNumber = tm.getLine1Number();

        //Get Network Country ISO Code
        String networkCountryISO = tm.getNetworkCountryIso();

        //Get SIM Country ISO Code
        String SIMCountryISO = tm.getSimCountryIso();

        //Get the device software version
        String softwareVersion = tm.getDeviceSoftwareVersion();

        //Get the Voice mail number
        String voiceMailNumber = tm.getVoiceMailNumber();

        //Get the Phone Type CDMA/GSM/NONE
        int phoneType = tm.getPhoneType();

        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                // your code
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                // your code
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                // your code
                break;
        }

        //Find whether the Phone is in Roaming, returns true if in roaming
        boolean isRoaming = tm.isNetworkRoaming();

        //Get the SIM state
        int SIMState = tm.getSimState();
        switch (SIMState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                // your code
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                // your code
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                // your code
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                // your code
                break;
            case TelephonyManager.SIM_STATE_READY:
                // your code
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                // your code
                break;

        }

        Log.d("Device Info", "doPermissionGrantedStuffs:IMEINumber: "+IMEINumber);
        Log.d("Device Info", "doPermissionGrantedStuffs:subscriberID: "+subscriberID);
        Log.d("Device Info", "doPermissionGrantedStuffs:SIMSerialNumber: "+SIMSerialNumber);
        Log.d("Device Info", "doPermissionGrantedStuffs:lineNumber: "+lineNumber);
        Log.d("Device Info", "doPermissionGrantedStuffs:networkCountryISO: "+networkCountryISO);
        Log.d("Device Info", "doPermissionGrantedStuffs:SIMCountryISO: "+SIMCountryISO);
        Log.d("Device Info", "doPermissionGrantedStuffs:softwareVersion: "+softwareVersion);
        Log.d("Device Info", "doPermissionGrantedStuffs:voiceMailNumber: "+voiceMailNumber);
        Log.d("Device Info", "doPermissionGrantedStuffs:isRoaming: "+isRoaming);
        Log.d("Device Info", "doPermissionGrantedStuffs:SIMState: "+SIMState);

    }

}
