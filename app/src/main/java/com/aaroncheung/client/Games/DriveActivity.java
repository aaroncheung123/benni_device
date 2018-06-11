package com.aaroncheung.client.Games;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aaroncheung.client.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class DriveActivity extends AppCompatActivity {

    String TAG = "debug_123";
    private String socket_url = "http://192.168.1.144:3000";

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

        socket.connect();
        socket.on("message", handleIncomingMessages);
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

    private void attemptSend(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Log.d(TAG, "attempt send");
        socket.emit("message", message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    public void forwardButtonClick(View view){
        attemptSend("w");
    }
    public void backwardButtonClick(View view){
        attemptSend("s");
    }
    public void leftButtonClick(View view){
        attemptSend("a");
    }
    public void rightButtonClick(View view){
        attemptSend("d");
    }
    public void stopButtonClick(View view){
        attemptSend("z");
    }
    public void driveToHomeButtonClick(View view){

    }
    public void voiceModeButtonClick(View view){

    }

}
