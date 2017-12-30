package com.startach.yedidim;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.startach.yedidim.Model.Event;

/**
 * Created by yb34982 on 25/12/2017.
 */

public class ChatHeadService extends Service {
    private WindowManager mWindowManager;
    private View mChatHeadView;
    private Event yedidimCurrentEvent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        yedidimCurrentEvent = (Event) intent.getExtras().get("event");
        return flags;
    }

    public ChatHeadService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //Inflate the chat head layout we created
        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.chat_head_event, null);


        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);


        params.gravity = Gravity.TOP | Gravity.RIGHT;
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mChatHeadView, params);

        final ImageView chatHeadImage = (ImageView) mChatHeadView.findViewById(R.id.chat_head_event_page);
        final ImageView chatHeadImageGet = (ImageView) mChatHeadView.findViewById(R.id.chat_head_get_event);


        chatHeadImage.setOnClickListener(v -> {
            //Open the chat conversation click.
                        Intent intent = new Intent(ChatHeadService.this, EventInfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra( "event", yedidimCurrentEvent);
                        startActivity(intent);
                        stopSelf();
        });

        chatHeadImageGet.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), " case :" + yedidimCurrentEvent.getDetails().getCase(), Toast.LENGTH_SHORT).show();
            stopSelf();
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatHeadView != null) mWindowManager.removeView(mChatHeadView);
    }

}

