package com.aaroncheung.client.Authentication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aaroncheung.client.HomeActivity;
import com.aaroncheung.client.HttpRequest;
import com.aaroncheung.client.R;
import com.aaroncheung.client.UserInformationSingleton;

import org.json.JSONException;
import org.json.JSONObject;


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

    public void loginClick(View view) {
        httpRequest.sendLoginGetRequest(emailLoginEditText.getText().toString());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    loginCheck();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    public void loginCheck() throws JSONException {
        JSONObject jsonObject = httpRequest.getMyJSONObject();

        if(jsonObject != null){
            String databasePassword = jsonObject.get("password").toString();
            password = passwordEditText.getText().toString();

            if(password.matches(databasePassword)){
                Toast.makeText(this, "Login Successful",
                        Toast.LENGTH_LONG).show();

                //Init user info singleton and adding email
                UserInformationSingleton userInfo = UserInformationSingleton.getInstance();
                userInfo.setEmail(jsonObject.get("email").toString());

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
            else{
                Toast.makeText(this, "Wrong Password",
                        Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Account Does Not Exist",
                    Toast.LENGTH_LONG).show();
        }
    }



    public void registerClick(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }




}
