package com.benniRobotics.client.Games;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.benniRobotics.client.Authentication.LoginActivity;
import com.benniRobotics.client.Authentication.RegisterActivity;
import com.benniRobotics.client.Games.Drive.ManualDriveActivity;
import com.benniRobotics.client.Helper.UserInformationSingleton;
import com.benniRobotics.client.R;

import org.json.JSONException;

public class GameListActivity extends AppCompatActivity{

    public GameListActivity(){

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_list_activity);

        //INITIALIZING THE ACTION BAR
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DAEDFE")));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.benni_robotics_logo);

    }


    public void speakClick(View view){
        startActivity(new Intent(GameListActivity.this, TypeSpeakActivity.class));
    }
    public void driveClick(View view){
        startActivity(new Intent(GameListActivity.this, ManualDriveActivity.class));
    }
}
