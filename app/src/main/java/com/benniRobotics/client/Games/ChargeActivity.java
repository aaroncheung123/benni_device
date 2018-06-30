package com.benniRobotics.client.Games;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.benniRobotics.client.Helper.UserInformationSingleton;
import com.benniRobotics.client.HomeActivity;
import com.benniRobotics.client.Networking.SocketIO;
import com.benniRobotics.client.R;

public class ChargeActivity extends SocketIO {

    private String TAG = "charge_act";
    private TextView headChargeTextView;
    private TextView bodyChargeTextView;
    private UserInformationSingleton userInformationSingleton;
    private HomeActivity homeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        userInformationSingleton = UserInformationSingleton.getInstance();

        homeActivity = HomeActivity.instance;
        homeActivity.updateChargeNumber();

        headChargeTextView = findViewById(R.id.headChargeTextView);
        bodyChargeTextView = findViewById(R.id.bodyChargeTextView);

        updateChargeNumbers();

        Log.d(TAG, String.valueOf(UserInformationSingleton.getInstance().getChargeProgressNumber()));

    }

    public void chargeToHomeButtonClick(View view) {
        startActivity(new Intent(ChargeActivity.this, HomeActivity.class));
    }

    public void updateChargeNumbers(){
        Log.d(TAG, "update charge numbers");

        headChargeTextView.setText(userInformationSingleton.getRobotHeadCharge() + "%");
        bodyChargeTextView.setText(userInformationSingleton.getRobotCharge() + "%");
    }




}
