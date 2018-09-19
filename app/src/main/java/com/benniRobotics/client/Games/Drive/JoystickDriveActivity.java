package com.benniRobotics.client.Games.Drive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.benniRobotics.client.Helper.UserInformationSingleton;
import com.benniRobotics.client.HomeActivity;
import com.benniRobotics.client.Networking.SocketIO;
import com.benniRobotics.client.R;

import org.json.JSONException;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class JoystickDriveActivity extends SocketIO {

    String TAG = "manual_drive";

    private Button forwardButton;
    private Button backwardButton;
    private Button leftButton;
    private Button rightButton;

    private ProgressBar manualDriveProgressBar;
    private TextView manualDriveTextView;
    private ToggleButton listenDriveToggleButton;

    private Intent UpdateVoiceDriveNumbersIntent;



    private Integer totalPoints;
    private UserInformationSingleton userInformationSingleton;

    public static JoystickDriveActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick_drive);
        instance = this;

        //INITIALIZING THE ACTION BAR
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DAEDFE")));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.benni_robotics_logo);

        userInformationSingleton = UserInformationSingleton.getInstance();
        totalPoints = 0;

        listenDriveToggleButton = findViewById(R.id.listenDriveToggleButton);


        forwardButton = findViewById(R.id.forwardButton);
        backwardButton = findViewById(R.id.backwardButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);

        manualDriveProgressBar = findViewById(R.id.manualDriveProgressBar);
        manualDriveTextView = findViewById(R.id.manualDriveTextView);

        manualDriveProgressBar.setProgress(userInformationSingleton.getDriveProgressNumber());
        manualDriveTextView.setText(userInformationSingleton.getDriveProgressNumber().toString() + "%");


        initializeForwardButton();
        initializeBackwardButton();
        initializeLeftButton();
        initializeRightButton();
        updateManualDriveNumbers();

    }

    @Override
    public void onDestroy() {
        try {
            attemptSend("stop drive");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    //--------------------------------------------------
    //
    // TIMER UPDATES THE PROGRESS BAR AND PERCENTAGE
    //
    //--------------------------------------------------
    public void updateManualDriveNumbers(){
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                manualDriveProgressBar.setProgress(userInformationSingleton.getDriveProgressNumber());
                manualDriveTextView.setText(userInformationSingleton.getDriveProgressNumber().toString() + "%");
                handler.postDelayed(this, 3600);
            }
        };
        handler.post(run);

    }



    //--------------------------------------------------
    //
    // Calculating the increase in points for this game
    //
    //--------------------------------------------------

    public void calculateTotalPoints(){
        Integer driveNumber = userInformationSingleton.getDriveProgressNumber();
        //Log.d(TAG, userInformationSingleton.getDriveProgressNumber().toString());
        totalPoints += 1;
        if(totalPoints == 1){
            driveNumber += 1;
            userInformationSingleton.setDriveProgressNumber(driveNumber);
            manualDriveProgressBar.setProgress(driveNumber);
            manualDriveTextView.setText(driveNumber.toString() + '%');
            totalPoints = 0;
        }
    }

    //--------------------------------------------------
    //
    // Home buttons
    //
    //--------------------------------------------------

    public void driveToHomeButtonClick(View view){
        startActivity(new Intent(JoystickDriveActivity.this, HomeActivity.class));
    }
    public void voiceDriveButtonClick(View view){
        startActivity(new Intent(JoystickDriveActivity.this, VoiceDriveActivity.class));
    }


    //--------------------------------------------------
    //
    // Move Forward
    //
    //--------------------------------------------------
    @SuppressLint("ClickableViewAccessibility")
    public void initializeForwardButton(){
        forwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Pressed
                if (event.getAction() == MotionEvent.ACTION_DOWN) try {
                    calculateTotalPoints();
                    attemptSend("forward");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
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


    //--------------------------------------------------

    // Move Backward

    //--------------------------------------------------
    @SuppressLint("ClickableViewAccessibility")
    public void initializeBackwardButton(){
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
    }


    //    --------------------------------------------------
//
//     Move Left
//
//    --------------------------------------------------
    @SuppressLint("ClickableViewAccessibility")
    public void initializeLeftButton(){
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
    }


    //    --------------------------------------------------
//
//     Move Right
//
//    --------------------------------------------------
    @SuppressLint("ClickableViewAccessibility")
    public void initializeRightButton(){
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


    public void listenDriveButtonClick(View view){
        if(listenDriveToggleButton.isChecked()){
            try {
                attemptSend("start drive listening");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                attemptSend("stop drive listening");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void switchClick2(View view) {
        startActivity(new Intent(JoystickDriveActivity.this, ManualDriveActivity.class));
    }

}
