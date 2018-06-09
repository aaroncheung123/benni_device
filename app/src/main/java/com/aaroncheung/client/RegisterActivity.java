package com.aaroncheung.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private String TAG = "debug_123";
    HttpRequest httpRequest;

    private EditText usernameRegisterEditText;
    private EditText passwordRegisterEditText1;
    private EditText passwordRegisterEditText2;
    private Button createRegisterButton;

    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameRegisterEditText = findViewById(R.id.usernameRegisterEditText);
        passwordRegisterEditText1 = findViewById(R.id.passwordRegisterEditText1);
        passwordRegisterEditText2 = findViewById(R.id.passwordRegisterEditText2);
        createRegisterButton = findViewById(R.id.createRegisterButton);

        httpRequest = new HttpRequest(this);
    }


    public void createRegisterClick(View view){
        username = usernameRegisterEditText.getText().toString();
        password = passwordRegisterEditText1.getText().toString();

        Log.d(TAG, username);
        Log.d(TAG, password);

        JSONObject jsonBodyPost = new JSONObject();
        try {
            jsonBodyPost.put("username", username);
            jsonBodyPost.put("password", password);
            //jsonBodyPost.put("info","Hello World");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        httpRequest.sendPostRequest(jsonBodyPost);

//        if(httpRequest.sendPostRequest(jsonBodyPost) == "USER EXISTS"){
//            //TOAST USERNAME ALREADY EXISTS
//        }
//        else if(httpRequest.sendPostRequest(jsonBodyPost) == "Error"){
//            //TOAST THERE WAS AN ERROR
//        }
    }

    public void backLoginClick(View view){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}
