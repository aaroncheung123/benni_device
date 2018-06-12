package com.aaroncheung.client.Games.Drive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.aaroncheung.client.HomeActivity;
import com.aaroncheung.client.R;
import com.aaroncheung.client.Networking.UserInformationSingleton;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;

import java.net.URISyntaxException;

public class ManualDriveActivity extends AppCompatActivity {

    String TAG = "debug_123";
    //private String socket_url = "http://192.168.1.144:3000";
    private String socket_url = "http://10.37.60.76:3000";
    private String email;

    Button forwardButton;
    Button backwardButton;
    Button leftButton;
    Button rightButton;

    private Socket socket;
    {
        Log.d(TAG, "Connecting to socket");
        try {
            socket = IO.socket(socket_url);
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);

        email = UserInformationSingleton.getInstance().getEmail();
        socket.connect();
        //socket.on(email, handleIncomingMessages);

        forwardButton = findViewById(R.id.forwardButton);
        backwardButton = findViewById(R.id.backwardButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);

        //*****************************
        // Forward
        //*****************************

        forwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    try {
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
        // Backward
        //*****************************

        backwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    try {
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
        // Left
        //*****************************

        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    try {
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
        // Right
        //*****************************

        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    try {
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

    private void attemptSend(String message) throws JSONException {
        if (TextUtils.isEmpty(message)) {
            return;
        }


        String finalMessage = email + ":" + message;
        Log.d(TAG, finalMessage);
        socket.emit("message", finalMessage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    public void driveToHomeButtonClick(View view){
        startActivity(new Intent(ManualDriveActivity.this, HomeActivity.class));
    }
    public void voiceDriveButtonClick(View view){
        startActivity(new Intent(ManualDriveActivity.this, VoiceDriveActivity.class));
    }

}
