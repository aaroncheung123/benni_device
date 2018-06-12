package com.aaroncheung.client.Games;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.aaroncheung.client.Networking.UserInformationSingleton;
import com.aaroncheung.client.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;

import java.net.URISyntaxException;

public class ChatActivity extends AppCompatActivity {

    ToggleButton toggleButton;
    String TAG = "debug_123";

    private String socket_url = "http://192.168.1.144:3000";
    private String email;

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
        setContentView(R.layout.activity_chat);

        toggleButton = (ToggleButton) findViewById(R.id.chatToggleButton);
        email = UserInformationSingleton.getInstance().getEmail();
        socket.connect();
    }

    private void attemptSend(String message) throws JSONException {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Log.d(TAG, "attempt send");

        String finalMessage = email + ":" + message;
        socket.emit("message", finalMessage);
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
