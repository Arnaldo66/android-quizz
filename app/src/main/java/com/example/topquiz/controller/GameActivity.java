package com.example.topquiz.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topquiz.R;
import com.example.topquiz.model.Question;
import com.example.topquiz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextGameQuestion;
    private Button mGameAnswer1Btn;
    private Button mGameAnswer2Btn;
    private Button mGameAnswer3Btn;
    private Button mGameAnswer4Btn;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int nNumberOfQuestions;
    private int mScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        nNumberOfQuestions = 4;
        mQuestionBank = this.generateQuestions();

        mTextGameQuestion = (TextView) findViewById(R.id.activity_game_question_text);
        mGameAnswer1Btn = (Button) findViewById(R.id.activity_game_answer1_btn);
        mGameAnswer2Btn = (Button) findViewById(R.id.activity_game_answer2_btn);
        mGameAnswer3Btn = (Button) findViewById(R.id.activity_game_answer3_btn);
        mGameAnswer4Btn = (Button) findViewById(R.id.activity_game_answer4_btn);

        mGameAnswer1Btn.setTag(0);
        mGameAnswer2Btn.setTag(1);
        mGameAnswer3Btn.setTag(2);
        mGameAnswer4Btn.setTag(3);

        mGameAnswer1Btn.setOnClickListener(this);
        mGameAnswer2Btn.setOnClickListener(this);
        mGameAnswer3Btn.setOnClickListener(this);
        mGameAnswer4Btn.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    private QuestionBank generateQuestions(){
        Question question1 = new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3));
    }

    private void displayQuestion(final Question question) {
        mTextGameQuestion.setText(question.getQuestion());
        mGameAnswer1Btn.setText(question.getChoiceList().get(0));
        mGameAnswer2Btn.setText(question.getChoiceList().get(1));
        mGameAnswer3Btn.setText(question.getChoiceList().get(2));
        mGameAnswer4Btn.setText(question.getChoiceList().get(3));
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        if(responseIndex == mCurrentQuestion.getAnswerIndex()) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, "Wrong answer!", Toast.LENGTH_SHORT).show();
        }

        if (--nNumberOfQuestions == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Well done!")
                    .setMessage("Your score is " + mScore)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        } else {
            mCurrentQuestion = mQuestionBank.getQuestion();
            displayQuestion(mCurrentQuestion);
        }
    }
}
