package com.benniRobotics.client.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

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
        userInformationSingleton = UserInformationSingleton.getInstance();

        //INSTANCE
        socketIO = SocketIO.instance;
        homeActivity = HomeActivity.instance;
        manualDriveActivity = ManualDriveActivity.instance;

        //BATTERY INIT
        batteryManager = (BatteryManager)getSystemService(BATTERY_SERVICE);
        batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        //EMOTIONAL STATE
        emotionalState = "Happy";

        backgroundTimer();
        decrementTimer();
        robotLowBatteryAlert();
        return super.onStartCommand(intent, flags, startId);
    }

    //----------------------------------------------
    //
    // Update progress every second
    //
    //----------------------------------------------
    public void backgroundTimer(){
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

                //BACKGROUND
                Log.d(TAG, "Timer+++: ");
                calculateCharge();
                setProgressNumbers();
                setHappinessIndex();
                try {
                    sendHappinessIndexNumber();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //HOME ACTIVITY
                homeActivity.updateDisplayNumbers();
                handler.postDelayed(this, 3600);


            }
        };
        handler.post(run);
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

                handler.postDelayed(this, 21600);

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
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

                //BACKGROUND
                Log.d(TAG, "Check for low battery ");
                if(UserInformationSingleton.getInstance().getRobotCharge() <= 20){

                    AlertDialog alertDialog = new AlertDialog.Builder(homeActivity)
                        .setTitle("Battery Low")
                        .setMessage("The Battery for Benni the Robot is low. Please Power Down and Charge")
                        .create();

                    alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    alertDialog.show();
                }
                handler.postDelayed(this, 3600);
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
    // BATTERY
    //
    //----------------------------------------------
    public void calculateCharge(){
        int totalCharge = batteryLevel; //average of robot battery and device
        userInformationSingleton.setChargeProgressNumber(totalCharge);
    }

    //----------------------------------------------
    //
    // SET PROGRESS NUMBERS
    //
    //----------------------------------------------
    public void setProgressNumbers(){
        driveProgressNumber = userInformationSingleton.getDriveProgressNumber();
        chatProgressNumber = userInformationSingleton.getChatProgressNumber();
        mathProgressNumber = userInformationSingleton.getMathProgressNumber();
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
    // SET HAPPINESS INDEX
    //
    //----------------------------------------------
    public void setHappinessIndex(){
        happinessIndexNumber = (driveProgressNumber + chatProgressNumber + mathProgressNumber)/4;
        userInformationSingleton.setHappinessIndexNumber(happinessIndexNumber);
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
        else if(happinessIndexNumber > 60){
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
