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

        initializeManualDriveTimer();
    }

    @Override
    public void processSocketIOCommands(String command) {
        Log.d(TAG, "processSocketIOCommands" + command);
        if(command.matches("add chatProgress")){
            Integer chatProgress = userInformationSingleton.getChatProgressNumber() + 5;
            Log.d(TAG, "adding "+ chatProgress.toString());
            userInformationSingleton.setChatProgressNumber(chatProgress);
        }
        else if(command.matches("listening")){
            listenToggleButton.setChecked(true);
        }
        else if(command.matches("stop listening")){
            listenToggleButton.setChecked(false);
        }
        else {
            Log.d(TAG, "processSocketIOCommands");
            String[] parts = command.split("-");
            Log.d(TAG, command + "THIS IS THE COMMAND");
            Log.d(TAG, parts[0]);
            Log.d(TAG, parts[1]);
            if (parts[0].matches("headBattery")) {
                userInformationSingleton.setRobotHeadCharge(Integer.parseInt(parts[1]));
                Log.d(TAG, String.valueOf(UserInformationSingleton.getInstance().getRobotHeadCharge()));
            }
            if (parts[0].matches("bodyBattery")) {
                userInformationSingleton.setRobotCharge(Integer.parseInt(parts[1]));
                Log.d(TAG, String.valueOf(UserInformationSingleton.getInstance().getChargeProgressNumber()));
            }
        }

    }


    public void chatToHomeButtonClick(View view) throws JSONException {
        startActivity(new Intent(ChatActivity.this, HomeActivity.class));
    }

    public void listenChatButtonClick(View view){
        if(listenToggleButton.isChecked()){
            try {
                attemptSend("listen");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
