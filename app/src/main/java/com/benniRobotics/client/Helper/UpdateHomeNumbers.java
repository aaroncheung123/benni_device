package com.benniRobotics.client.Helper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.benniRobotics.client.Games.ChargeActivity;
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
    private ManualDriveActivity manualDriveActivity;


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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        homeActivity = new HomeActivity();
        userInformationSingleton = UserInformationSingleton.getInstance();
        socketIO = SocketIO.instance;

        //EMOTIONAL STATE
        emotionalState = "Happy";

        backgroundTimer();
        sendHappinessLevel();
        setProgressNumbers();
        robotLowBatteryAlert();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
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

                handler.postDelayed(this, 7000);

            }
        };
        handler.post(run);
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
