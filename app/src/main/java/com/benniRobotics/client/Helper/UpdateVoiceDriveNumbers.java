package com.benniRobotics.client.Helper;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.benniRobotics.client.HomeActivity;
import com.benniRobotics.client.Networking.SocketIO;

import org.json.JSONException;

public class UpdateVoiceDriveNumbers extends Service {
    private UserInformationSingleton userInformationSingleton;

    public UpdateVoiceDriveNumbers() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        userInformationSingleton = UserInformationSingleton.getInstance();

        updateDriveNumbers();

        return super.onStartCommand(intent, flags, startId);
    }

    //----------------------------------------------
    //
    // Update progress every second
    //
    //----------------------------------------------
    public void updateDriveNumbers(){
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

               userInformationSingleton.setDriveProgressNumber(userInformationSingleton.getDriveProgressNumber() + 1);
               handler.postDelayed(this, 3600);
            }
        };
        handler.post(run);
    }

}
