package com.aaroncheung.client.Games;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aaroncheung.client.Networking.SocketIO;
import com.aaroncheung.client.R;

import org.json.JSONException;

public class MathActivity extends SocketIO {

    private TextView problemMathTextView;
    private EditText solutionMathEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        problemMathTextView = findViewById(R.id.problemMathTextView);
        solutionMathEditText = findViewById(R.id.solutionMathEditText);
        problemMathTextView.setText("3 + 4");


        //He says ""Hi, can you help me with my math homework"
        try {
            attemptSend("Math1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void submitMathButtonClick(View view) throws JSONException {
        if(solutionMathEditText.getText().toString().matches("7")){
            attemptSend("Turn Left");
            final Handler handler = new Handler();
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        attemptSend("Stop");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    handler.postDelayed(this, 10000);
                }
            };
            handler.post(run);
        }
        else{
            attemptSend("Change SadFace");
        }
    }
}
