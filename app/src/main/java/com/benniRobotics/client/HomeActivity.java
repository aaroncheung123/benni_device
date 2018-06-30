package com.benniRobotics.client;

import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.benniRobotics.client.Games.ChargeActivity;
import com.benniRobotics.client.Games.ChatActivity;
import com.benniRobotics.client.Games.Drive.ManualDriveActivity;
import com.benniRobotics.client.Games.MathActivity;
import com.benniRobotics.client.Helper.TimerService;
import com.benniRobotics.client.Helper.UpdateHomeNumbers;
import com.benniRobotics.client.Networking.SocketIO;
import com.benniRobotics.client.Helper.UserInformationSingleton;

import org.json.JSONException;


public class HomeActivity extends SocketIO {

    private String TAG = "debug_123";
    public static HomeActivity instance;

    //Progress Bars
    private ProgressBar driveProgressBar;
    private ProgressBar chatProgressBar;
    private ProgressBar mathProgressBar;
    private ProgressBar chargeProgressBar;
    private Intent updateNumbersIntent;

    //Happiness Index
    private TextView happinessIndexTextView;

    //Helper
    private UserInformationSingleton userInformationSingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userInformationSingleton = UserInformationSingleton.getInstance();
        instance = this;

        Log.i(TAG, "Home onCreate");

        //STARTING SERVICE TIMER
        updateNumbersIntent = new Intent(getApplicationContext(), UpdateHomeNumbers.class);
        startService(updateNumbersIntent);

        //INITIALIZING PROGRESS BARS
        driveProgressBar = findViewById(R.id.driveProgressBar);
        chatProgressBar = findViewById(R.id.chatProgressBar);
        mathProgressBar = findViewById(R.id.mathProgressBar);
        chargeProgressBar = findViewById(R.id.chargeProgressBar);
        happinessIndexTextView = findViewById(R.id.happinessIndex);
        updateChargeNumber();
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

    //----------------------------------------------
    //
    // UPDATE ALL OF THE PROGRESS BARS AND THE HAPPINESS INDEX NUMBER
    //
    //----------------------------------------------
    public void updateDisplayNumbers(){
        driveProgressBar.setProgress(userInformationSingleton.getDriveProgressNumber());
        chatProgressBar.setProgress(userInformationSingleton.getChatProgressNumber());
        mathProgressBar.setProgress(userInformationSingleton.getMathProgressNumber());
        happinessIndexTextView.setText(userInformationSingleton.getHappinessIndexNumber() + "%");
    }

    public void updateChargeNumber(){
        chargeProgressBar.setProgress(userInformationSingleton.getRobotHeadCharge());
    }

}
