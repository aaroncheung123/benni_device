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

    private String TAG = "debug_123";
    private TextView headChargeTextView;
    private TextView bodyChargeTextView;

    private ProgressBar chargeProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);

        chargeProgressBar = findViewById(R.id.chargeProgressBar);
        headChargeTextView = findViewById(R.id.headChargeTextView);
        bodyChargeTextView = findViewById(R.id.bodyChargeTextView);

        Log.d(TAG, String.valueOf(UserInformationSingleton.getInstance().getChargeProgressNumber()));
    }

    public void chargeToHomeButtonClick(View view) {
        startActivity(new Intent(ChargeActivity.this, HomeActivity.class));
    }

    @Override
    public void processSocketIOCommands(String command) {
        Log.d(TAG, "processSocketIOCommands");
        String[] parts = command.split(" ");
        if (parts[0].matches("head-battery")) {
            chargeProgressBar.setProgress(Integer.parseInt(parts[1]));
            headChargeTextView.setText(Integer.parseInt(parts[1]) + "%");
            Log.d(TAG, String.valueOf(UserInformationSingleton.getInstance().getChargeProgressNumber()));
        }

        if (parts[0].matches("body-battery")) {
            bodyChargeTextView.setText(Integer.parseInt(parts[1]) + "%");
            Log.d(TAG, String.valueOf(UserInformationSingleton.getInstance().getChargeProgressNumber()));
        }

        if (parts[0].matches("min-low-charge")) {
            UserInformationSingleton.getInstance().setMinLowCharge(Integer.valueOf(parts[1]));
        }
    }
}
