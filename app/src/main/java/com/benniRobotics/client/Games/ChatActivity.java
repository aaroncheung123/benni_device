package com.benniRobotics.client.Games;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.benniRobotics.client.HomeActivity;
import com.benniRobotics.client.Networking.SocketIO;
import com.benniRobotics.client.Helper.UserInformationSingleton;
import com.benniRobotics.client.R;

import org.json.JSONException;

public class ChatActivity extends SocketIO {

    String TAG = "Chat_act";

    private TextView chatTextView;
    private ProgressBar chatProgressBar;
    private UserInformationSingleton userInformationSingleton;
    private ToggleButton listenToggleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userInformationSingleton = UserInformationSingleton.getInstance();
        chatTextView = findViewById(R.id.chatTextView);
        chatProgressBar = findViewById(R.id.chatProgressBar);
        listenToggleButton = findViewById(R.id.listenToggleButton);

        try {
            attemptSend("start chat");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initializeManualDriveTimer();
    }


//    @Override
//    public void processSocketIOCommands(String command) {
//
//    }

    public void listenChatButtonClick(View view){
        if(listenToggleButton.isChecked()){
            try {
                attemptSend("listen");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                attemptSend("stop listening");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void chatToHomeButtonClick(View view) throws JSONException {
        startActivity(new Intent(ChatActivity.this, HomeActivity.class));
    }


    //--------------------------------------------------
    //
    // Timer to check drive progress
    //
    //--------------------------------------------------

    public void initializeManualDriveTimer(){
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                chatProgressBar.setProgress(userInformationSingleton.getChatProgressNumber());
                chatTextView.setText(userInformationSingleton.getChatProgressNumber().toString() + "%");
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(run);
    }

}
