package com.aaroncheung.client.Networking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.aaroncheung.client.Helper.UserInformationSingleton;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;

import java.net.URISyntaxException;

public class SocketIO extends AppCompatActivity{


    String TAG = "debug_123";
    private String url = UserInformationSingleton.getInstance().getSERVER_URL();
    private String email;
    public static SocketIO instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        email = UserInformationSingleton.getInstance().getEmail();
        socket.connect();
        socket.on(email, handleIncomingMessages);

    }

    private Socket socket;
    {
        Log.d(TAG, "Connecting to socket");
        try {
            socket = IO.socket(url);
        } catch (URISyntaxException e) {}
    }


    private Emitter.Listener handleIncomingMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            SocketIO.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, args[0].toString());
                    processSocketIOCommands(args[0].toString());
                }
            });
        }
    };

    public void attemptSend(String message) throws JSONException {
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

    public void processSocketIOCommands(String message){ }
}
