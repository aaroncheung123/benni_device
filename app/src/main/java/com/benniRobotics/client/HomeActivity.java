package com.benniRobotics.client;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.benniRobotics.client.Authentication.LoginActivity;
import com.benniRobotics.client.Authentication.RegisterActivity;
import com.benniRobotics.client.Games.ChatActivity;
import com.benniRobotics.client.Games.GameListActivity;
import com.benniRobotics.client.Helper.UpdateHomeNumbers;
import com.benniRobotics.client.Networking.HttpRequest;
import com.benniRobotics.client.Networking.SocketIO;
import com.benniRobotics.client.Helper.UserInformationSingleton;

import org.json.JSONException;
import org.json.JSONObject;


public class HomeActivity extends SocketIO {

    private String TAG = "debug_123";
    public static HomeActivity instance;
    private HttpRequest httpRequest;

    //Progress Bars
    private static ProgressBar driveProgressBar;
    private static ProgressBar chatProgressBar;
    private static ProgressBar mathProgressBar;
    private static ProgressBar chargeProgressBar;
    private Intent updateNumbersIntent;

    //Happiness Index
    private static TextView happinessIndexTextView;

    //Love Index
    private static TextView loveIndexTextView;

    //Helper
    private UserInformationSingleton userInformationSingleton;

    //Emotional State
    private static ImageView emotionalImageState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userInformationSingleton = UserInformationSingleton.getInstance();
        instance = this;

        //INITIALIZING THE ACTION BAR
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DAEDFE")));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.benni_robotics_logo);

        Log.i(TAG, "Home onCreate");

        //INITIALIZING PROGRESS BARS
        driveProgressBar = findViewById(R.id.driveProgressBar);
        chatProgressBar = findViewById(R.id.chatProgressBar);
        mathProgressBar = findViewById(R.id.speakProgressBar);
        //chargeProgressBar = findViewById(R.id.chargeProgressBar);
        happinessIndexTextView = findViewById(R.id.happinessIndex);
        loveIndexTextView = findViewById(R.id.loveIndex2);
        emotionalImageState = findViewById(R.id.emotionBig);

        httpRequest = new HttpRequest(this);

        updateChargeNumber();

        //STARTING SERVICE TIMER
        updateNumbersIntent = new Intent(getApplicationContext(), UpdateHomeNumbers.class);
        startService(updateNumbersIntent);

    }

    @Override
    protected void onPause() {
//
//        JSONObject jsonFinal = new JSONObject();
//        JSONObject jsonID = new JSONObject();
//        JSONObject jsonProgressNumbersBody = new JSONObject();
//        try {
//            jsonID.put("$oid", userInformationSingleton.getCurrentID());
//
//            jsonProgressNumbersBody.put("loved", userInformationSingleton.getLoveIndexNumber());
//            jsonProgressNumbersBody.put("happy", userInformationSingleton.getHappinessIndexNumber());
//            jsonFinal.put("id", jsonID);
//            jsonFinal.put("progressNumbers", jsonProgressNumbersBody);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        httpRequest.sendUpdatePostRequest(jsonFinal);
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(httpRequest.getResponse().matches("1")){
//                    Log.d(TAG, "Success sendPostRequest");
//                }
//                else if(httpRequest.getResponse().matches("0")){
//                    Log.d(TAG, "Fail sendPostRequest");
//                }
//                else{
//                    Log.d(TAG, "No sendPostRequest");
//                }
//            }
//        }, 2000);
        super.onPause();
    }


    public void driveButtonClick(View view){
        startActivity(new Intent(HomeActivity.this, GameListActivity.class));
    }
    public void chatButtonClick(View view){
        startActivity(new Intent(HomeActivity.this, ChatActivity.class));
    }
//    public void mathButtonClick(View view){
//        startActivity(new Intent(HomeActivity.this, MathActivity.class));
//    }
//    public void chargeButtonClick(View view){
//        startActivity(new Intent(HomeActivity.this, ChargeActivity.class));
//    }

    //----------------------------------------------
    //
    // UPDATE ALL OF THE PROGRESS BARS AND THE HAPPINESS INDEX NUMBER
    //
    //----------------------------------------------
    public void updateDisplayNumbers(){
        userInformationSingleton = UserInformationSingleton.getInstance();
        driveProgressBar.setProgress(userInformationSingleton.getDriveProgressNumber());
        chatProgressBar.setProgress(userInformationSingleton.getChatProgressNumber());
       // mathProgressBar.setProgress(userInformationSingleton.getMathProgressNumber());
        happinessIndexTextView.setText(userInformationSingleton.getHappinessIndexNumber() + "%");
        loveIndexTextView.setText(userInformationSingleton.getLoveIndexNumber() + "%");
        setEmotionState();
    }

    public void updateChargeNumber(){
        //chargeProgressBar.setProgress(userInformationSingleton.getRobotHeadCharge());
    }

    public void setEmotionState(){
        String emotion = userInformationSingleton.getCurrentEmotionalState();
        if(emotion.equalsIgnoreCase("Happy")){
            emotionalImageState.setImageResource(R.drawable.h1);
        }
        else if(emotion.equalsIgnoreCase("Bored")){
            emotionalImageState.setImageResource(R.drawable.boredface);
        }
        else if(emotion.equalsIgnoreCase("Sad")){
            emotionalImageState.setImageResource(R.drawable.sadface);
        }
        else if(emotion.equalsIgnoreCase("Mad")){
            emotionalImageState.setImageResource(R.drawable.mad);
        }
        else if(emotion.equalsIgnoreCase("Broken")){
            emotionalImageState.setImageResource(R.drawable.broken);
        }
    }


}
