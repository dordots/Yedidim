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

import com.startach.yedidim.Model.Event;

/**
 * Created by yb34982 on 25/12/2017.
 */

public class ChatHeadService extends Service {
    public static final String SERVICE_EVENT = "event_service";

    private WindowManager mWindowManager;
    private View mChatHeadView;
    private Event yedidimCurrentEvent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        yedidimCurrentEvent = (Event) intent.getExtras().get(SERVICE_EVENT);
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
        final ImageView chatHeadImageGiveUp = (ImageView) mChatHeadView.findViewById(R.id.chat_head_giveup_event);


        chatHeadImage.setOnClickListener(v -> {
            //Open the chat conversation click.
            returnBackToEventActivity(EventState.PreDecision);
        });

        chatHeadImageGet.setOnClickListener(v -> {
            returnBackToEventActivity(EventState.Take);
        });

        chatHeadImageGiveUp.setOnClickListener(v -> {
            returnBackToEventActivity(EventState.GiveUp);
        });


    }

    private void returnBackToEventActivity(EventState eventState) {

        Intent intent = EventInfoActivity.Companion.createIntent(ChatHeadService.this, yedidimCurrentEvent,eventState);
        startActivity(intent);
        stopSelf();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatHeadView != null) mWindowManager.removeView(mChatHeadView);
    }

}

