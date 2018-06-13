package com.aaroncheung.client.Games.Drive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aaroncheung.client.HomeActivity;
import com.aaroncheung.client.Networking.SocketIO;
import com.aaroncheung.client.Networking.UserInformationSingleton;
import com.aaroncheung.client.R;
import org.json.JSONException;

public class ManualDriveActivity extends SocketIO {

    String TAG = "debug_123";

    private Button forwardButton;
    private Button backwardButton;
    private Button leftButton;
    private Button rightButton;

    private ProgressBar manualDriveProgressBar;
    private TextView manualDriveTextView;

    private Integer totalPoints;
    private UserInformationSingleton userInformationSingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);

        userInformationSingleton = UserInformationSingleton.getInstance();
        totalPoints = 0;

        forwardButton = findViewById(R.id.forwardButton);
        backwardButton = findViewById(R.id.backwardButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);

        manualDriveProgressBar = findViewById(R.id.manualDriveProgressBar);
        manualDriveTextView = findViewById(R.id.manualDriveTextView);

        manualDriveProgressBar.setProgress(userInformationSingleton.getDriveProgressNumber());
        manualDriveTextView.setText(userInformationSingleton.getDriveProgressNumber().toString() + "%");

        //*****************************
        // Move Forward
        //*****************************

        forwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    try {
                        calculateTotalPoints();
                        attemptSend("forward");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    try {
                        attemptSend("stop");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });

        //*****************************
        // Move Backward
        //*****************************

        backwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    try {
                        calculateTotalPoints();
                        attemptSend("backward");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    try {
                        attemptSend("stop");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });

        //*****************************
        // Move Left
        //*****************************

        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    try {
                        calculateTotalPoints();
                        attemptSend("left");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    try {
                        attemptSend("stop");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });

        //*****************************
        // Move Right
        //*****************************

        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    try {
                        calculateTotalPoints();
                        attemptSend("right");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    try {
                        attemptSend("stop");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
    }

    public void calculateTotalPoints(){
        Integer driveNumber = userInformationSingleton.getDriveProgressNumber();
        Log.d(TAG, userInformationSingleton.getDriveProgressNumber().toString());
        totalPoints += 1;
        if(totalPoints == 3){
            driveNumber += 1;
            userInformationSingleton.setDriveProgressNumber(driveNumber);
            manualDriveProgressBar.setProgress(driveNumber);
            manualDriveTextView.setText(driveNumber.toString() + '%');
            totalPoints = 0;
        }
    }

    public void driveToHomeButtonClick(View view){
        startActivity(new Intent(ManualDriveActivity.this, HomeActivity.class));
    }
    public void voiceDriveButtonClick(View view){
        startActivity(new Intent(ManualDriveActivity.this, VoiceDriveActivity.class));
    }

}
