package com.aaroncheung.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class MainActivity extends AppCompatActivity {

    String TAG = "debug_123";
    HttpRequest httpRequest;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    private String username;
    private String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);


        Log.d(TAG, "onCreate");
        httpRequest = new HttpRequest(this);
    }




    public void registerClick(View view){
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        Log.d(TAG, username);
        Log.d(TAG, password);

        JSONObject jsonBodyPost = new JSONObject();
        try {
            jsonBodyPost.put("username", username);
            jsonBodyPost.put("password", password);
            jsonBodyPost.put("info","Hello World");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        httpRequest.sendPostRequest(jsonBodyPost);
    }

    public void loginClick(View view){
        //attemptSend();
    }


}
