package com.aaroncheung.client.Games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aaroncheung.client.Authentication.LoginActivity;
import com.aaroncheung.client.Authentication.RegisterActivity;
import com.aaroncheung.client.HomeActivity;
import com.aaroncheung.client.R;
import com.aaroncheung.client.UserInformationSingleton;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class DriveActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_drive);

        email = UserInformationSingleton.getInstance().getEmail();

        socket.connect();
        socket.on(email, handleIncomingMessages);
    }

    private Emitter.Listener handleIncomingMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            DriveActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, args[0].toString());
                }
            });
        }
    };

    private void attemptSend(String message) throws JSONException {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Log.d(TAG, "attempt send");

        String finalMessage = email + ":" + message;
        socket.emit("message", finalMessage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    public void forwardButtonClick(View view) throws JSONException {
        attemptSend("w");
    }
    public void backwardButtonClick(View view) throws JSONException {
        attemptSend("s");
    }
    public void leftButtonClick(View view) throws JSONException {
        attemptSend("a");
    }
    public void rightButtonClick(View view) throws JSONException {
        attemptSend("d");
    }
    public void stopButtonClick(View view) throws JSONException {
        attemptSend("z");
    }
    public void driveToHomeButtonClick(View view){
        startActivity(new Intent(DriveActivity.this, HomeActivity.class));
    }
    public void voiceModeButtonClick(View view){

    }

}
