package com.aaroncheung.client;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import com.aaroncheung.client.Networking.UserInformationSingleton;

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
    UserInformationSingleton userInformationSingleton;
    final private Integer decrementNumber = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userInformationSingleton = UserInformationSingleton.getInstance();

        //SETTING PROGRESS BARS
        driveProgressBar = findViewById(R.id.driveProgressBar);
        chatProgressBar = findViewById(R.id.chatProgressBar);
        mathProgressBar = findViewById(R.id.mathProgressBar);
        chargeProgressBar = findViewById(R.id.chargeProgressBar);
        happinessIndexTextView = findViewById(R.id.happinessIndex);
        emotionalState = "Happy";


        Log.i(TAG, "Home onCreate");

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

                //GETTING PROGRESS NUMBERS
                driveProgressNumber = userInformationSingleton.getDriveProgressNumber();
                chatProgressNumber = userInformationSingleton.getChatProgressNumber();
                mathProgressNumber = userInformationSingleton.getMathProgressNumber();
                chargeProgressNumber = userInformationSingleton.getChargeProgressNumber();

                //Log.d(TAG, chatProgressNumber.toString());


                //SETTING PROGRESS BARS
                driveProgressBar.setProgress(driveProgressNumber);
                chatProgressBar.setProgress(chatProgressNumber);
                mathProgressBar.setProgress(mathProgressNumber);
                chargeProgressBar.setProgress(chargeProgressNumber);

                //HAPPINESS INDEX CALCULATIONS
                happinessIndexNumber = (driveProgressNumber + chargeProgressNumber + mathProgressNumber + chargeProgressNumber)/4;
                happinessIndexTextView.setText(happinessIndexNumber.toString() + "%");
                try {
                    sendHappinessIndexNumber();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                if(chargeProgressNumber > 0) {
                    userInformationSingleton.setChargeProgressNumber(chargeProgressNumber - decrementNumber);
                }

                handler.postDelayed(this, 10000);
            }
        };
        handler.post(run);
    }

    public void sendHappinessIndexNumber() throws JSONException {
        if(happinessIndexNumber > 80){
            Log.d(TAG, "send HIN1: " + emotionalState);
            if(!emotionalState.contains("Happy")){
                emotionalStateChange = true;
            }
            emotionalState = "Happy";
        }
        else if(happinessIndexNumber > 60){
            Log.d(TAG, "send HIN2: " + emotionalState);
            if(!emotionalState.contains("Bored")){
                emotionalStateChange = true;
            }
            emotionalState = "Bored";
        }
        else if(happinessIndexNumber > 30){
            Log.d(TAG, "send HIN3: " + emotionalState);
            if(!emotionalState.contains("Sad")){
                emotionalStateChange = true;
            }
            emotionalState = "Sad";
        }
        else if(happinessIndexNumber > 1){
            Log.d(TAG, "send HIN4: " + emotionalState);
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
            attemptSend(emotionalState.toString());
            emotionalStateChange = false;
        }

    }


}
