package com.aaroncheung.client.Games;

import android.content.Intent;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aaroncheung.client.HomeActivity;
import com.aaroncheung.client.R;

public class ChargeActivity extends AppCompatActivity {

    private String TAG = "debug_123";
    private BatteryManager batteryManager;
    private int batteryLevel;
    private TextView headChargeTextView;
    private TextView bodyChargeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);

        batteryManager = (BatteryManager)getSystemService(BATTERY_SERVICE);
        batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        headChargeTextView = findViewById(R.id.headChargeTextView);
        bodyChargeTextView = findViewById(R.id.bodyChargeTextView);

        headChargeTextView.setText(batteryLevel + "%");
        Log.d(TAG, String.valueOf(batteryLevel));
    }

    public void chargeToHomeButtonClick(View view){
        startActivity(new Intent(ChargeActivity.this, HomeActivity.class));
    }
}
