package com.aaroncheung.client.Games.Drive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.aaroncheung.client.HomeActivity;
import com.aaroncheung.client.Networking.SocketIO;
import com.aaroncheung.client.R;
import org.json.JSONException;


public class VoiceDriveActivity extends SocketIO {

    String TAG = "debug_123";
    ToggleButton toggleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_drive);
        toggleButton = (ToggleButton) findViewById(R.id.voiceToggleButton);
    }

    public void voiceActivatedHomeButtonClick(View view){
        startActivity(new Intent(VoiceDriveActivity.this, HomeActivity.class));
    }
    public void manualDriveButtonClick(View view){
        startActivity(new Intent(VoiceDriveActivity.this, ManualDriveActivity.class));
    }
    public void voiceToggleButtonClick(View view) throws JSONException {
        if(toggleButton.isChecked()){
            attemptSend("listen");
        }
        else{
            attemptSend("stop listening");
        }
    }


}
