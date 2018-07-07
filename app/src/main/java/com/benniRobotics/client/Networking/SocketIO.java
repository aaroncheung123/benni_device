package com.benniRobotics.client.Networking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.benniRobotics.client.Games.ChatActivity;
import com.benniRobotics.client.Helper.UserInformationSingleton;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;

import java.net.URISyntaxException;

public abstract class SocketIO extends AppCompatActivity{


    String TAG = "socket";
    private String url = UserInformationSingleton.getInstance().getSERVER_URL();
    private UserInformationSingleton userInformationSingleton;
    private String email;
    public static SocketIO instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInformationSingleton = UserInformationSingleton.getInstance();

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
                    Log.d(TAG, args[0].toString() + "listener");
//                    String[] parts = args[0].toString().split("-");
//                    Log.d(TAG, args[0].toString() + "THIS IS THE COMMAND");
//                    Log.d(TAG, parts[0]);
//                    Log.d(TAG, parts[1]);
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

    public void processSocketIOCommands(String command){
        Log.d(TAG, "processSocketIOCommands");
        if(command.matches("stop drive listening")){

        }
        String[] parts = command.split("-");
        Log.d(TAG, parts[0]);
        if(parts.length > 1) {
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

    public void updateChargeNumbers(){ }
}
