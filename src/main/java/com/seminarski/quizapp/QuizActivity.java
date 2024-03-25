package com.seminarski.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class QuizActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE = "scoreRes";
    private static final long COUNTDOWN_MILLISEC = 30000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_TIME_LEFT = "keyTimeLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionNumber;
    private TextView textViewTimer;
    private RadioGroup rGroup;
    private RadioButton rbPrvi;
    private RadioButton rbDrugi;
    private RadioButton rbTreci;
    private RadioButton rbCetvrti;
    private Button buttonSubmitQuestion;

    private ArrayList<Question> questionList;

    private ColorStateList rbDefaultColor;
    private ColorStateList countdownDefaultColor;

    private CountDownTimer countdownTimer;
    private long timeLeftMillisec;

    private int questionCounter;
    private int questionCounterTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;

    private long backPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        RelativeLayout relativeLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        getWindow().setStatusBarColor(ContextCompat.getColor(QuizActivity.this,R.color.DPMain));

        textViewQuestion = findViewById(R.id.textview_question);
        textViewScore = findViewById(R.id.textview_current_score);
        textViewQuestionNumber = findViewById(R.id.textview_question_number);
        textViewTimer = findViewById(R.id.textview_timer);
        rGroup = findViewById(R.id.radio_group);
        rbPrvi = findViewById(R.id.radio_answer1);
        rbDrugi = findViewById(R.id.radio_answer2);
        rbTreci = findViewById(R.id.radio_answer3);
        rbCetvrti = findViewById(R.id.radio_answer4);
        buttonSubmitQuestion = findViewById(R.id.button_submit_question);

        rbDefaultColor = rbPrvi.getTextColors();
        countdownDefaultColor = textViewTimer.getTextColors();

        if(savedInstanceState == null) {

            QuizDb dbHelp = new QuizDb(this);
            questionList = dbHelp.getAllQuestions();
            //questionCounterTotal = questionList.size();
            questionCounterTotal = 10;
            Collections.shuffle(questionList);

            showNextQuestion();
        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            if(questionList == null){
                finish();
            }
            //questionCounterTotal = questionList.size();
            questionCounterTotal = 10;
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftMillisec = savedInstanceState.getLong(KEY_TIME_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if(!answered){
                startCountdown();
            } else {
                updateCountdownText();
                showSolution();
            }
        }

        buttonSubmitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered){
                    if (rbPrvi.isChecked() || rbDrugi.isChecked() ||rbTreci.isChecked() || rbCetvrti.isChecked()){
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select an answer!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });

    }
    private void showNextQuestion(){
        rbPrvi.setTextColor(rbDefaultColor);
        rbDrugi.setTextColor(rbDefaultColor);
        rbTreci.setTextColor(rbDefaultColor);
        rbCetvrti.setTextColor(rbDefaultColor);
        rGroup.clearCheck();

        if(questionCounter < questionCounterTotal){
            currentQuestion = questionList.get(questionCounter);
            textViewQuestion.setText(currentQuestion.getQuestion());
            rbPrvi.setText(currentQuestion.getAnswer1());
            rbDrugi.setText(currentQuestion.getAnswer2());
            rbTreci.setText(currentQuestion.getAnswer3());
            rbCetvrti.setText(currentQuestion.getAnswer4());

            questionCounter++;

            textViewQuestionNumber.setText("Question " + questionCounter + "/" + questionCounterTotal);
            answered = false;
            buttonSubmitQuestion.setText("CONFIRM");

            timeLeftMillisec = COUNTDOWN_MILLISEC;
            startCountdown();

        } else {
            finishQuiz();
        }
    }

    private void startCountdown(){
        countdownTimer = new CountDownTimer(timeLeftMillisec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMillisec = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timeLeftMillisec = 0;
                updateCountdownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountdownText(){
        //int minutes = (int) (timeLeftMillisec / 1000) / 60;
        int seconds = (int) (timeLeftMillisec / 1000) % 60;

        String timeFormat = String.format(Locale.getDefault(), "%02d",seconds);
        textViewTimer.setText(timeFormat);

        if(timeLeftMillisec < 10000){
            textViewTimer.setTextColor(Color.RED);
        } else {
            textViewTimer.setTextColor(countdownDefaultColor);
        }
    }

    private void checkAnswer(){
        answered = true;
        countdownTimer.cancel();

        RadioButton rbSelected = findViewById(rGroup.getCheckedRadioButtonId());
        //onaj odgovor, tj njegova pozicija
        int correctAnswer = rGroup.indexOfChild(rbSelected) + 1;
        if (correctAnswer == currentQuestion.getCorrectAnswer()){
            final KonfettiView konfettiView = findViewById(R.id.konfettiView);
                    konfettiView.build()
                            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                            .setDirection(0.0, 359.0)
                            .setSpeed(1f, 5f)
                            .setFadeOutEnabled(true)
                            .setTimeToLive(2000L)
                            .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                            .addSizes(new Size(12, 5f))
                            .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                            .streamFor(300, 1000L);
            if(timeLeftMillisec < 20000) {
                score++;
            } else {
                score += 2;
            }
            textViewScore.setText("Score: " + score);

        } else {
            if(score > 0){
                score--;
                textViewScore.setText("Score: " + score);
            }
        }
        showSolution();
    }

    private void showSolution(){
        rbPrvi.setTextColor(Color.RED);
        rbDrugi.setTextColor(Color.RED);
        rbTreci.setTextColor(Color.RED);
        rbCetvrti.setTextColor(Color.RED);

        switch (currentQuestion.getCorrectAnswer()){
            case 1:
                rbPrvi.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is correct!");
                break;
            case 2:
                rbDrugi.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct!");
                break;
            case 3:
                rbTreci.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct!");
                break;
            case 4:
                rbCetvrti.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 4 is correct!");
                break;
        }

        if(questionCounter < questionCounterTotal){
            buttonSubmitQuestion.setText("NEXT");
        } else {
            buttonSubmitQuestion.setText("FINISH");
        }
    }

    private void finishQuiz(){
        Intent resIntent = new Intent();
        resIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resIntent);
        Toast.makeText(QuizActivity.this, "You scored " + score + " points!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            finishQuiz();
        } else {
            Toast.makeText(this, "Press again if you want to leave", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countdownTimer != null){
            countdownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE,score);
        outState.putInt(KEY_QUESTION_COUNT,questionCounter);
        outState.putLong(KEY_TIME_LEFT,timeLeftMillisec);
        outState.putBoolean(KEY_ANSWERED,answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST,questionList);
    }
}