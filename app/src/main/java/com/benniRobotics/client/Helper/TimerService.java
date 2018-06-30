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

    //INSTANCES OF ACTIVITIES
    private SocketIO socketIO;
    private HomeActivity homeActivity;
    private SocketIO chargeActivity;
    private ManualDriveActivity manualDriveActivity;

    //BATTERY
    private BatteryManager batteryManager;
    private int batteryLevel;

    //PROGRESS NUMBERS
    private int driveProgressNumber;
    private int chatProgressNumber;
    private int mathProgressNumber;

    //HAPPINESS INDEX NUMBER
    private int happinessIndexNumber;

    //EMOTIONAL STATE
    private boolean emotionalStateChange;
    private String emotionalState;

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

        //INSTANCE
        socketIO = SocketIO.instance;
        homeActivity = HomeActivity.instance;
        chargeActivity = ChargeActivity.instance;
        manualDriveActivity = ManualDriveActivity.instance;
        userInformationSingleton = UserInformationSingleton.getInstance();

        //BATTERY INIT
        batteryManager = (BatteryManager)getSystemService(BATTERY_SERVICE);
        batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        //EMOTIONAL STATE
        emotionalState = "Happy";

        decrementTimer();
        robotLowBatteryAlert();
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
                try {
                    sendHappinessIndexNumber();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                handler.postDelayed(this, 3600);

            }
        };
        handler.post(run);
    }


    //----------------------------------------------
    //
    // Alerts user about low robot battery
    //
    //----------------------------------------------
    public void robotLowBatteryAlert(){
        if(userInformationSingleton.getRobotCharge() == -1)
            userInformationSingleton.setRobotCharge(100);
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

                //BACKGROUND
                Log.d(TAG, "Check for low battery ");
                if(userInformationSingleton.getRobotCharge() <= 20){

                    Context context = getApplicationContext();
                    CharSequence text = "The Battery for Benni the Robot is low. Please Power Down and Charge";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if(userInformationSingleton.getRobotCharge() <= 5) {
//                    android.hardware.usb.UsbManager.
                }

                    handler.postDelayed(this, 10000);
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

    //----------------------------------------------
    //
    // SEND ROBOT CORRECT EMOTION TO DISPLAY
    //
    //----------------------------------------------
    public void sendHappinessIndexNumber() throws JSONException {
        if(happinessIndexNumber > 80){
            if(!emotionalState.contains("Happy")){
                emotionalStateChange = true;
            }
            emotionalState = "Happy";
        }
        else if(happinessIndexNumber > 50){
            if(!emotionalState.contains("Bored")){
                emotionalStateChange = true;
            }
            emotionalState = "Bored";
        }
        else if(happinessIndexNumber > 30){
            if(!emotionalState.contains("Sad")){
                emotionalStateChange = true;
            }
            emotionalState = "Sad";
        }
        else if(happinessIndexNumber > 1){
            if(!emotionalState.contains("Mad")){
                emotionalStateChange = true;
            }
            emotionalState = "Mad";
        }
        else if(happinessIndexNumber == 1){
            if(!emotionalState.contains("Broken")){
                emotionalStateChange = true;
            }
            emotionalState = "Broken";
        }

        if(emotionalStateChange){
            Log.d(TAG, "SENDING!!!!!: " + emotionalState);
            socketIO.attemptSend(emotionalState.toString());
            emotionalStateChange = false;
        }
    }


}
