package com.benniRobotics.client.Games;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.benniRobotics.client.Games.Drive.ManualDriveActivity;
import com.benniRobotics.client.Helper.UserInformationSingleton;
import com.benniRobotics.client.HomeActivity;
import com.benniRobotics.client.Networking.SocketIO;
import com.benniRobotics.client.R;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MathActivity extends SocketIO {

    private UserInformationSingleton userInformationSingleton;

    private TextView problemOneMathTextView;
    private EditText solutionMathEditText;
    private TextView problemoperatorMathTextView;
    private TextView problemTwoMathTextView;
    private TextView problemTextMathTextView;
    private TextView problemEqualsMathTextView;

    private ProgressBar mathProgressBar;
    private TextView mathTextView;


    private int firstNumber;
    private int secondNumber;
    private int solution;



    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        updateMathGameNumbers();

        mathProgressBar = findViewById(R.id.mathProgressBar);
        mathTextView = findViewById(R.id.mathTextView);

        userInformationSingleton = UserInformationSingleton.getInstance();

        problemOneMathTextView = findViewById(R.id.problemOneMathTextView);
        problemoperatorMathTextView = findViewById(R.id.problemOperatorMathTextView);
        problemTwoMathTextView = findViewById(R.id.problemTwoMathTextView);
        problemTextMathTextView = findViewById(R.id.problemTextMathTextView);
        problemEqualsMathTextView = findViewById(R.id.problemEqualsMathTextView);

        problemOneMathTextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        problemoperatorMathTextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        problemTwoMathTextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        solutionMathEditText = findViewById(R.id.solutionMathEditText);


        //He says ""Hi, can you help me with my math homework"
        try {
            attemptSend("Math1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void submitMathButtonClick(View view) throws JSONException {
        if(solutionMathEditText.getText().toString().matches(String.valueOf(solution))){
            attemptSend("CorrectMath");
            attemptSend("Turn Left");

            problemOneMathTextView.setText("");
            problemoperatorMathTextView.setText("");
            problemTwoMathTextView.setText("");
            problemoperatorMathTextView.setText("");
            solutionMathEditText.setText("");

            problemTextMathTextView.setText("Good Job!");
            userInformationSingleton.setMathProgressNumber(userInformationSingleton.getMathProgressNumber() + 1);

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
            problemTextMathTextView.setText("Try Again!");
            attemptSend("Change SadFace");
        }

    }

    public void newProblemSelectionButtonClick(View view){
        problemTextMathTextView.setText("");
        solutionMathEditText.setText("");
        firstNumber = rand.nextInt(10);
        secondNumber = rand.nextInt(10);
        int add_sub = rand.nextInt(1);
        problemEqualsMathTextView.setText("=");
        if(add_sub == 0){
            solution = firstNumber + secondNumber;
            problemOneMathTextView.setText(String.valueOf(firstNumber));
            problemoperatorMathTextView.setText("+");
            problemTwoMathTextView.setText(String.valueOf(secondNumber));
        }
        else {
            solution = firstNumber - secondNumber;
            problemOneMathTextView.setText(String.valueOf(firstNumber));
            problemoperatorMathTextView.setText("-");
            problemTwoMathTextView.setText(String.valueOf(secondNumber));
        }
    }

    public void returnHomeOnClickButton(View view){
        startActivity(new Intent(MathActivity.this, HomeActivity.class));
    }

    //--------------------------------------------------
    //
    // TIMER UPDATES THE PROGRESS BAR AND PERCENTAGE
    //
    //--------------------------------------------------
    public void updateMathGameNumbers(){
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                mathProgressBar.setProgress(userInformationSingleton.getDriveProgressNumber());
                mathTextView.setText(userInformationSingleton.getDriveProgressNumber().toString() + "%");
                handler.postDelayed(this, 3600);
            }
        };
        handler.post(run);

    }

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
