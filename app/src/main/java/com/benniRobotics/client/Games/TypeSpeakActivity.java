package com.benniRobotics.client.Games;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.benniRobotics.client.Helper.UserInformationSingleton;
import com.benniRobotics.client.HomeActivity;
import com.benniRobotics.client.Networking.SocketIO;
import com.benniRobotics.client.R;

import org.json.JSONException;

import java.util.Random;

public class TypeSpeakActivity extends SocketIO {

        private UserInformationSingleton userInformationSingleton;

        private EditText speakEditText;
        private TextView speakResponseTextView;



        private ProgressBar speakProgressBar;
        private TextView speakTextView;


        private int firstNumber;
        private int secondNumber;
        private int solution;



        private Random rand = new Random();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.type_speak_activity);

            //updateSpeakGameNumbers();

            speakProgressBar = findViewById(R.id.speakProgressBar);
            speakTextView = findViewById(R.id.speakTextView);

            userInformationSingleton = UserInformationSingleton.getInstance();

            speakEditText = findViewById(R.id.sentanceToSay);
            speakResponseTextView = findViewById(R.id.speakGameResponse);

        }

        public void submitSpeakButtonClick(View view){
        String sentence = speakEditText.getText().toString();

        try {
            attemptSend(sentence);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //userInformationSingleton.setMathProgressNumber(userInformationSingleton.getMathProgressNumber() + 1);

        }
        public void refreshSpeakButtonClick(View view){
            speakResponseTextView.setText(userInformationSingleton.getLastResponse());
        }



        public void returnHomeOnClickButton(View view){
            startActivity(new Intent(TypeSpeakActivity.this, HomeActivity.class));
        }


//        //--------------------------------------------------
//        //
//        // TIMER UPDATES THE PROGRESS BAR AND PERCENTAGE
//        //
//        //--------------------------------------------------
//        public void updateMathGameNumbers(){
//            final Handler handler = new Handler();
//            Runnable run = new Runnable() {
//                @Override
//                public void run() {
//                    mathProgressBar.setProgress(userInformationSingleton.getDriveProgressNumber());
//                    mathTextView.setText(userInformationSingleton.getDriveProgressNumber().toString() + "%");
//                    handler.postDelayed(this, 3600);
//                }
//            };
//            handler.post(run);
//
//        }

//    public void subtractionSelectionButtonClick(View view){
//        firstNumber = rand.nextInt(10);
//        secondNumber = rand.nextInt(10);
//        solution = firstNumber - secondNumber;
//        problemOneMathTextView.setText(String.valueOf(firstNumber));
//        problemoperatorMathTextView.setText("-");
//        problemTwoMathTextView.setText(String.valueOf(secondNumber));
//    }
//
//    public void multiplicationSelectionButtonClick(View view){
//        firstNumber = rand.nextInt(10);
//        secondNumber = rand.nextInt(10);
//        solution = firstNumber * secondNumber;
//        problemOneMathTextView.setText(String.valueOf(firstNumber));
//        problemoperatorMathTextView.setText("*");
//        problemTwoMathTextView.setText(String.valueOf(secondNumber));
//    }


}
