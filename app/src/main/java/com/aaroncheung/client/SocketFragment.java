package com.aaroncheung.client;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketFragment extends Fragment {


    String TAG = "debug_123";
    private String socket_url = "http://192.168.1.144:3000";

    private Socket socket;
    {
        Log.d(TAG, "mSocket");
        try {
            socket = IO.socket(socket_url);
        } catch (URISyntaxException e) {}
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.socket_fragment, container, false);


        socket.connect();
        //socket.on("message", handleIncomingMessages());

        Button testClick = (Button) view.findViewById(R.id.testButton);
        testClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                attemptSend();
            }});

        return view;
    }


    private void attemptSend() {
        String message = "Hello World";
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Log.d(TAG, "attempt send");
        socket.emit("message", message);
    }


//    private Emitter.Listener handleIncomingMessages = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    String message;
//                    try {
//                        message = data.getString("message");
//                    } catch (JSONException e) {
//                        return;
//                    }
//                    Log.d(TAG, "handle messages");
//                    // add the message to view
//                    //addMessage(username, message);
//                }
//            });
//        }
//    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
