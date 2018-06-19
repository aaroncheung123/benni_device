package com.aaroncheung.client;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class TimerService extends Service {

    HomeActivity homeActivity;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        homeActivity = HomeActivity.instance;
        startTimer();
        return super.onStartCommand(intent, flags, startId);
    }


    public void startTimer(){
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                homeActivity.testing();
                handler.postDelayed(this, 10000);
            }
        };
        handler.post(run);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
