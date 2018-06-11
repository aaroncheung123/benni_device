package com.aaroncheung.client;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class LoginActivity extends AppCompatActivity {

    String TAG = "debug_123";
    HttpRequest httpRequest;

    private EditText emailLoginEditText;
    private EditText passwordEditText;

    private String email;
    private String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailLoginEditText = findViewById(R.id.emailLoginEditText);
        passwordEditText = findViewById(R.id.passwordLoginEditText);

        Log.d(TAG, "Login onCreate");
        httpRequest = new HttpRequest(this);
    }

    public void loginClick(View view){
        httpRequest.sendLoginGetRequest(emailLoginEditText.getText().toString());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(httpRequest.getResponse().matches("1")){
                    Log.d(TAG, "Success sendLoginGetRequest");
                    toastLoginResponse("1");
                    //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
                else if(httpRequest.getResponse().matches("0")){
                    toastLoginResponse("0");
                    Log.d(TAG, "Fail sendLoginGetRequest");
                }
            }
        }, 5000);
    }

    public void toastLoginResponse(String response){
        if(response.matches("0")){
            Toast.makeText(this, "Error: Contact EI Robotics",
                    Toast.LENGTH_SHORT).show();
        }
        else if(response.matches("1")){
            Toast.makeText(this, "Login Successful",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void registerClick(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }




}
