package com.benniRobotics.client.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.benniRobotics.client.Games.ChargeActivity;
import com.benniRobotics.client.Games.Drive.ManualDriveActivity;
import com.benniRobotics.client.HomeActivity;
import com.benniRobotics.client.Networking.SocketIO;

import org.json.JSONException;

public class TimerService extends Service {

    private String TAG = "debug_123";
    private UserInformationSingleton userInformationSingleton;

    //HELPER
    final private Integer decrementNumber = 1;


    //----------------------------------------------
    //
    // TIMER SERVICE SET UP
    //
    //----------------------------------------------

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        userInformationSingleton = UserInformationSingleton.getInstance();

        decrementTimer();
        return super.onStartCommand(intent, flags, startId);
    }


    //----------------------------------------------
    //
    // Decrement Timer
    //
    //----------------------------------------------
    public void decrementTimer(){
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

                //BACKGROUND
                Log.d(TAG, "Decrement+++: ");
                decrementProgressNumbers();
                handler.postDelayed(this, 15000);
            }
        };
        handler.post(run);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //----------------------------------------------
    //
    // DECREMENT PROGRESS NUMBERS
    //
    //----------------------------------------------
    public void decrementProgressNumbers(){
        int driveProgressNumber = userInformationSingleton.getDriveProgressNumber();
        int chatProgressNumber = userInformationSingleton.getChatProgressNumber();
        int mathProgressNumber = userInformationSingleton.getMathProgressNumber();
        if(driveProgressNumber > 0) {
            userInformationSingleton.setDriveProgressNumber(driveProgressNumber - decrementNumber);
        }
        if(chatProgressNumber > 0) {
            userInformationSingleton.setChatProgressNumber(chatProgressNumber - decrementNumber);
        }
        if(mathProgressNumber > 0) {
            userInformationSingleton.setMathProgressNumber(mathProgressNumber - decrementNumber);
        }
    }


}
