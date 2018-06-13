package com.aaroncheung.client.Networking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;

import java.net.URISyntaxException;

public class SocketIO extends AppCompatActivity{


    String TAG = "debug_123";
    private String url = UserInformationSingleton.getInstance().getSERVER_URL();
    private String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        email = UserInformationSingleton.getInstance().getEmail();
        socket.connect();

    }

    private Socket socket;
    {
        Log.d(TAG, "Connecting to socket");
        try {
            socket = IO.socket(url);
        } catch (URISyntaxException e) {}
    }

    protected void attemptSend(String message) throws JSONException {
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
}
