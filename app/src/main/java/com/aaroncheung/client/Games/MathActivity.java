package com.aaroncheung.client.Games;

import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aaroncheung.client.Networking.SocketIO;
import com.aaroncheung.client.R;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MathActivity extends SocketIO {

    private TextView problemOneMathTextView;
    private EditText solutionMathEditText;
    private TextView problemoperatorMathTextView;
    private TextView problemTwoMathTextView;

    private int firstNumber;
    private int secondNumber;
    private int solution;


    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        problemOneMathTextView = findViewById(R.id.problemOneMathTextView);
        problemoperatorMathTextView = findViewById(R.id.problemOperatorMathTextView);
        problemTwoMathTextView = findViewById(R.id.problemTwoMathTextView);

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

    public void additionSelectionButtonClick(View view){
        firstNumber = rand.nextInt(10);
        secondNumber = rand.nextInt(10);
        solution = firstNumber + secondNumber;
        problemOneMathTextView.setText(firstNumber);
        problemoperatorMathTextView.setText("+");
        problemTwoMathTextView.setText(secondNumber);
    }

    public void subtractionSelectionButtonClick(View view){
        firstNumber = rand.nextInt(10);
        secondNumber = rand.nextInt(10);
        solution = firstNumber - secondNumber;
        problemOneMathTextView.setText(firstNumber);
        problemoperatorMathTextView.setText("-");
        problemTwoMathTextView.setText(secondNumber);
    }

    public void multiplicationSelectionButtonClick(View view){
        firstNumber = rand.nextInt(10);
        secondNumber = rand.nextInt(10);
        solution = firstNumber * secondNumber;
        problemOneMathTextView.setText(firstNumber);
        problemoperatorMathTextView.setText("*");
        problemTwoMathTextView.setText(secondNumber);
    }

}
