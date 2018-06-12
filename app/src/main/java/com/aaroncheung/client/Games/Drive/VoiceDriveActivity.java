package com.aaroncheung.client.Games.Drive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.aaroncheung.client.HomeActivity;
import com.aaroncheung.client.Networking.UserInformationSingleton;
import com.aaroncheung.client.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;

import java.net.URISyntaxException;

public class VoiceDriveActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_voice_drive);
        toggleButton = (ToggleButton) findViewById(R.id.voiceToggleButton);

        email = UserInformationSingleton.getInstance().getEmail();
        socket.connect();
    }

    private void attemptSend(String message) throws JSONException {
        if (TextUtils.isEmpty(message)) {
            return;
        }

        String finalMessage = email + ":" + message;
        Log.d(TAG, finalMessage);
        socket.emit("message", finalMessage);
    }

    public void voiceActivatedHomeButtonClick(View view){
        startActivity(new Intent(VoiceDriveActivity.this, HomeActivity.class));
    }
    public void manualDriveButtonClick(View view){
        startActivity(new Intent(VoiceDriveActivity.this, ManualDriveActivity.class));
    }
    public void voiceToggleButtonClick(View view) throws JSONException {
        if(toggleButton.isChecked()){
            attemptSend("listen");
        }
        else{
            attemptSend("stop listening");
        }
    }


}
