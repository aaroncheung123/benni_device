package com.aaroncheung.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aaroncheung.client.Games.ChargeActivity;
import com.aaroncheung.client.Games.ChatActivity;
import com.aaroncheung.client.Games.DriveActivity;
import com.aaroncheung.client.Games.MathActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void driveButtonClick(View view){
        startActivity(new Intent(HomeActivity.this, DriveActivity.class));
    }
    public void chatButtonClick(View view){
        startActivity(new Intent(HomeActivity.this, ChatActivity.class));
    }
    public void mathButtonClick(View view){
        startActivity(new Intent(HomeActivity.this, MathActivity.class));
    }
    public void chargeButtonClick(View view){
        startActivity(new Intent(HomeActivity.this, ChargeActivity.class));
    }
}
