package com.benniRobotics.client.Helper;

import android.app.Service;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.benniRobotics.client.Games.Drive.ManualDriveActivity;
import com.benniRobotics.client.HomeActivity;
import com.benniRobotics.client.Networking.SocketIO;

import org.json.JSONException;

import static android.content.ContentValues.TAG;

public class UpdateHomeNumbers extends Service {
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

    public UpdateHomeNumbers() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        userInformationSingleton = UserInformationSingleton.getInstance();


        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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
    // send happiness level
    //
    //----------------------------------------------
    public void sendHappinessLevel(){
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

                //BACKGROUND
                Log.d(TAG, "Send happiness level+++: ");
                try {
                    sendHappinessIndexNumber();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                handler.postDelayed(this, 10000);

            }
        };
        handler.post(run);
    }

    //----------------------------------------------
    //
    // BATTERY
    //
    //----------------------------------------------
    public void calculateCharge(){
        int totalCharge = batteryLevel; //average of robot battery and device
//        userInformationSingleton.setChargeProgressNumber(totalCharge);
    }

    //----------------------------------------------
    //
    // SET HAPPINESS INDEX
    //
    //----------------------------------------------
    public void setHappinessIndex(){
        happinessIndexNumber = (driveProgressNumber + chatProgressNumber + mathProgressNumber)/3;
        userInformationSingleton.setHappinessIndexNumber(happinessIndexNumber);
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
