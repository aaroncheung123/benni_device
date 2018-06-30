package com.benniRobotics.client.Authentication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.benniRobotics.client.Networking.HttpRequest;
import com.benniRobotics.client.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private String TAG = "debug_123";
    HttpRequest httpRequest;


    private EditText firstNameRegisterEditText;
    private EditText lastNameRegisterEditText;
    private EditText phoneRegisterEditText;
    private EditText addressRegisterEditText;

    private EditText emailRegisterEditText;
    private EditText passwordRegisterEditText1;
    private EditText passwordRegisterEditText2;
    private Button createRegisterButton;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String email;
    private String password;
    private String confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameRegisterEditText = findViewById(R.id.firstNameRegisterEditText);
        lastNameRegisterEditText = findViewById(R.id.lastNameRegisterEditText);
        phoneRegisterEditText = findViewById(R.id.phoneRegisterEditText);
        addressRegisterEditText = findViewById(R.id.addressRegisterEditText);

        emailRegisterEditText = findViewById(R.id.emailRegisterEditText);
        passwordRegisterEditText1 = findViewById(R.id.passwordRegisterEditText1);
        passwordRegisterEditText2 = findViewById(R.id.passwordRegisterEditText2);
        createRegisterButton = findViewById(R.id.createRegisterButton);

        httpRequest = new HttpRequest(this);
        Log.d(TAG, "Register onCreate");
    }


    public void createRegisterClick(View view){
        String response;
        firstName = firstNameRegisterEditText.getText().toString();
        lastName = lastNameRegisterEditText.getText().toString();
        phone = phoneRegisterEditText.getText().toString();
        address = addressRegisterEditText.getText().toString();
        email = emailRegisterEditText.getText().toString();
        password = passwordRegisterEditText1.getText().toString();
        confirmPassword = passwordRegisterEditText2.getText().toString();

        Log.d(TAG, email);
        Log.d(TAG, password);

        if(firstName.matches("")){
            Toast.makeText(this, "Please enter first name",
                    Toast.LENGTH_LONG).show();
        }
        else if(lastName.matches("")){
            Toast.makeText(this, "Please enter last name",
                    Toast.LENGTH_LONG).show();
        }
        else if(phone.matches("")){
            Toast.makeText(this, "Please enter phone",
                    Toast.LENGTH_LONG).show();
        }
        else if(address.matches("")){
            Toast.makeText(this, "Please enter address",
                    Toast.LENGTH_LONG).show();
        }
        else if(email.matches("")){
            Toast.makeText(this, "Please enter email",
                    Toast.LENGTH_LONG).show();
        }
        else if(password.matches("")){
            Toast.makeText(this, "Please enter password",
                    Toast.LENGTH_LONG).show();
        }
        else if(confirmPassword.matches("")){
            Toast.makeText(this, "Please confirm password",
                    Toast.LENGTH_LONG).show();
        }
        else if(password.matches(confirmPassword) == false){
            Toast.makeText(this, "Passwords do not match",
                    Toast.LENGTH_LONG).show();
        }
        else{
            JSONObject jsonFinal = new JSONObject();
            JSONObject jsonInfoBody = new JSONObject();
            JSONObject jsonProgressNumbersBody = new JSONObject();
            try {
                jsonInfoBody.put("firstName", firstName);
                jsonInfoBody.put("lastName", lastName);
                jsonInfoBody.put("phone", phone);
                jsonInfoBody.put("address", address);
                jsonInfoBody.put("email", email);
                jsonInfoBody.put("password", password);

                jsonProgressNumbersBody.put("drive", 100);
                jsonProgressNumbersBody.put("chat", 100);
                jsonProgressNumbersBody.put("math", 100);
                jsonProgressNumbersBody.put("charge", 100);


                jsonFinal.put("info", jsonInfoBody);
                jsonFinal.put("progressNumbers", jsonProgressNumbersBody);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            httpRequest.sendPostRequest(jsonFinal);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(httpRequest.getResponse().matches("1")){
                        Log.d(TAG, "Success sendPostRequest");
                        toastPostResponse("1");
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                    else if(httpRequest.getResponse().matches("0")){
                        toastPostResponse("0");
                        Log.d(TAG, "Fail sendPostRequest");
                    }
                }
            }, 2000);


        }

    }

    public void toastPostResponse(String response){
        if(response.matches("0")){
            Toast.makeText(this, "Error: Contact EI Robotics",
                    Toast.LENGTH_LONG).show();
        }
        else if(response.matches("1")){
            Toast.makeText(this, "Successfully Created",
                        Toast.LENGTH_LONG).show();
        }
    }

    public void backLoginClick(View view){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}
