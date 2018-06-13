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
import com.aaroncheung.client.Networking.UserInformationSingleton;


public class HomeActivity extends AppCompatActivity {

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
    UserInformationSingleton userInformationSingleton;
    final private Integer decrementNumber = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        driveProgressBar = findViewById(R.id.driveProgressBar);
        chatProgressBar = findViewById(R.id.chatProgressBar);
        mathProgressBar = findViewById(R.id.mathProgressBar);
        chargeProgressBar = findViewById(R.id.chargeProgressBar);
        happinessIndexTextView = findViewById(R.id.happinessIndex);

        userInformationSingleton = UserInformationSingleton.getInstance();

        Log.i(TAG, "Home onCreate");

        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

                //GETTING PROGRESS NUMBERS FROM SINGLETON AND DECREMENTING
                if(happinessIndexNumber > 0){
                    driveProgressNumber = userInformationSingleton.getDriveProgressNumber() - decrementNumber;
                    chatProgressNumber = userInformationSingleton.getChatProgressNumber() - decrementNumber;
                    mathProgressNumber = userInformationSingleton.getMathProgressNumber() - decrementNumber;
                    chargeProgressNumber = userInformationSingleton.getChargeProgressNumber() - decrementNumber;
                }
                //SETTING PROGRESS NUMBERS IN SINGLETON
                userInformationSingleton.setDriveProgressNumber(driveProgressNumber);
                userInformationSingleton.setChatProgressNumber(chatProgressNumber);
                userInformationSingleton.setMathProgressNumber(mathProgressNumber);
                userInformationSingleton.setChargeProgressNumber(chargeProgressNumber);

                //SETTING PROGRESS BARS
                driveProgressBar.setProgress(driveProgressNumber);
                chatProgressBar.setProgress(chatProgressNumber);
                mathProgressBar.setProgress(mathProgressNumber);
                chargeProgressBar.setProgress(chargeProgressNumber);

                //HAPPINESS INDEX CALCULATIONS
                happinessIndexNumber = (driveProgressNumber + chargeProgressNumber + mathProgressNumber + chargeProgressNumber)/4;
                happinessIndexTextView.setText(happinessIndexNumber.toString());

                handler.postDelayed(this, 10000);
            }
        };
        handler.post(run);
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


}
