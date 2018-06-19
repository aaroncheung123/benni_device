package com.aaroncheung.client;

import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aaroncheung.client.Games.ChargeActivity;
import com.aaroncheung.client.Games.ChatActivity;
import com.aaroncheung.client.Games.Drive.ManualDriveActivity;
import com.aaroncheung.client.Games.MathActivity;
import com.aaroncheung.client.Networking.SocketIO;
import com.aaroncheung.client.Helper.UserInformationSingleton;

import org.json.JSONException;


public class HomeActivity extends SocketIO {

    private String TAG = "debug_123";
    private ProgressBar driveProgressBar;
    private ProgressBar chatProgressBar;
    private ProgressBar mathProgressBar;
    private ProgressBar chargeProgressBar;
    private Integer driveProgressNumber;
    private Integer chatProgressNumber;
    private Integer mathProgressNumber;
    private Integer chargeProgressNumber;
    private TextView happinessIndexTextView;
    private Integer happinessIndexNumber;
    private boolean emotionalStateChange;
    private String emotionalState;
    private BatteryManager batteryManager;
    private int batteryLevel;
    UserInformationSingleton userInformationSingleton;
    final private Integer decrementNumber = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userInformationSingleton = UserInformationSingleton.getInstance();

        Log.i(TAG, "Home onCreate");

        //SETTING PROGRESS BARS
        driveProgressBar = findViewById(R.id.driveProgressBar);
        chatProgressBar = findViewById(R.id.chatProgressBar);
        mathProgressBar = findViewById(R.id.mathProgressBar);
        chargeProgressBar = findViewById(R.id.chargeProgressBar);
        happinessIndexTextView = findViewById(R.id.happinessIndex);
        emotionalState = "Happy";

        //BATTERY LIFE
        batteryManager = (BatteryManager)getSystemService(BATTERY_SERVICE);
        batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        initializeHomeTimer();
    }


    public void driveButtonClick(View view){
        startActivity(new Intent(HomeActivity.this, ManualDriveActivity.class));
    }
    public void chatButtonClick(View view){
        startActivity(new Intent(HomeActivity.this, ChatActivity.class));
    }
    public void mathButtonClick(View view){
        startActivity(new Intent(HomeActivity.this, MathActivity.class));
    }
    public void chargeButtonClick(View view){
        startActivity(new Intent(HomeActivity.this, ChargeActivity.class));
    }


    public void initializeHomeTimer(){
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                calculateCharge();
                getProgressFromSingleton();
                setProgressBars();
                setHappinessIndex();
                decrementProgressNumbers();
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
    public void calculateCharge(){
        int totalCharge = batteryLevel; //average of robot battery and device
        userInformationSingleton.setChargeProgressNumber(totalCharge);
    }

    public void getProgressFromSingleton(){
        driveProgressNumber = userInformationSingleton.getDriveProgressNumber();
        chatProgressNumber = userInformationSingleton.getChatProgressNumber();
        mathProgressNumber = userInformationSingleton.getMathProgressNumber();
        chargeProgressNumber = userInformationSingleton.getChargeProgressNumber();
    }
    public void setProgressBars(){
        driveProgressBar.setProgress(driveProgressNumber);
        chatProgressBar.setProgress(chatProgressNumber);
        mathProgressBar.setProgress(mathProgressNumber);
        chargeProgressBar.setProgress(chargeProgressNumber);
    }

    public void setHappinessIndex(){
        happinessIndexNumber = (driveProgressNumber + chargeProgressNumber + mathProgressNumber)/4;
        happinessIndexTextView.setText(happinessIndexNumber.toString() + "%");
    }

    public void decrementProgressNumbers(){
        //GETTING PROGRESS NUMBERS FROM SINGLETON AND DECREMENTING
        if(driveProgressNumber > 0) {
            userInformationSingleton.setDriveProgressNumber(driveProgressNumber - decrementNumber);
        }
        if(chargeProgressNumber > 0) {
            userInformationSingleton.setChatProgressNumber(chatProgressNumber - decrementNumber);
        }
        if(mathProgressNumber > 0) {
            userInformationSingleton.setMathProgressNumber(mathProgressNumber - decrementNumber);
        }
    }

    public void sendHappinessIndexNumber() throws JSONException {
        if(happinessIndexNumber > 80){
            //Log.d(TAG, "HIN1: " + emotionalState);
            if(!emotionalState.contains("Happy")){
                emotionalStateChange = true;
            }
            emotionalState = "Happy";
        }
        else if(happinessIndexNumber > 60){
            //Log.d(TAG, "HIN2: " + emotionalState);
            if(!emotionalState.contains("Bored")){
                emotionalStateChange = true;
            }
            emotionalState = "Bored";
        }
        else if(happinessIndexNumber > 30){
            //Log.d(TAG, "HIN3: " + emotionalState);
            if(!emotionalState.contains("Sad")){
                emotionalStateChange = true;
            }
            emotionalState = "Sad";
        }
        else if(happinessIndexNumber > 1){
            //Log.d(TAG, "HIN4: " + emotionalState);
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
            attemptSend(emotionalState.toString());
            emotionalStateChange = false;
        }

    }


}
