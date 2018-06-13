package com.aaroncheung.client.Games;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.aaroncheung.client.Networking.SocketIO;
import com.aaroncheung.client.Networking.UserInformationSingleton;
import com.aaroncheung.client.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;

import java.net.URISyntaxException;

public class ChatActivity extends SocketIO {

    String TAG = "debug_123";
    ToggleButton toggleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toggleButton = (ToggleButton) findViewById(R.id.chatToggleButton);
    }

    public void chatToggleButtonClick(View view) throws JSONException {
        if(toggleButton.isChecked()){
            attemptSend("listen");
        }
        else{
            attemptSend("stop listening");
        }
    }
}
