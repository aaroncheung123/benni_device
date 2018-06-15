package com.aaroncheung.client.Games;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.aaroncheung.client.Games.Drive.ManualDriveActivity;
import com.aaroncheung.client.HomeActivity;
import com.aaroncheung.client.Networking.SocketIO;
import com.aaroncheung.client.Networking.UserInformationSingleton;
import com.aaroncheung.client.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;

import java.net.URISyntaxException;

public class ChatActivity extends SocketIO {

    String TAG = "debug_123";

    TextView chatTextView;
    ProgressBar chatProgressBar;
    UserInformationSingleton userInformationSingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userInformationSingleton = UserInformationSingleton.getInstance();
        chatTextView = findViewById(R.id.chatTextView);
        chatProgressBar = findViewById(R.id.chatProgressBar);

        initializeManualDriveTimer();
        try {
            attemptSend("chat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processSocketIOCommands(String command) {
        Log.d(TAG, "processSocketIOCommands");
        if(command.matches("add chatProgress")){
            Log.d(TAG, "+1");
            userInformationSingleton.setChatProgressNumber(userInformationSingleton.getChatProgressNumber() + 5);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "CHAT STOP");
        try {
            attemptSend("stop listening");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void chatToHomeButtonClick(View view){
        startActivity(new Intent(ChatActivity.this, HomeActivity.class));
    }

    //*****************************
    // Timer to check drive progress
    //*****************************

    public void initializeManualDriveTimer(){
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                chatProgressBar.setProgress(userInformationSingleton.getChatProgressNumber());
                chatTextView.setText(userInformationSingleton.getChatProgressNumber().toString() + "%");
                handler.postDelayed(this, 10000);
            }
        };
        handler.post(run);
    }

}
