package com.msushanth.mathbraintrainer;

import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;




public class Main2Activity extends Activity {

    LinearLayout playAgainMenu;
    TextView resultTextView;
    TextView pointsTextView;
    TextView timeTextView;
    TextView sumTextView;
    TextView finalScoreTextView;
    TextView accuracyTextView;
    ProgressBar progressBar;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    ArrayList<Integer> lastClicks = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int numOfQuestions = 0;
    String scoreStr = "0";
    String numOfQuestionsStr = "0";
    int totalSeconds = 30;
    int counter = 0;




    public int generateAnAnswer(int correctAnswer) {
        Random rand = new Random();
        int randomIntMax = 199;
        int randomInt = rand.nextInt(randomIntMax);

        if(correctAnswer < 10) {
            randomIntMax = 10;
            randomInt = rand.nextInt(randomIntMax);
        }
        else if(correctAnswer < 100) {
            randomIntMax = 100;
            while(randomInt < 10 || randomInt > 100) {
                randomInt = rand.nextInt(randomIntMax);
            }
        }
        else {
            randomIntMax = 199;
            while(randomInt < 100 || randomInt > 199) {
                randomInt = rand.nextInt(randomIntMax);
            }
        }

        return randomInt;
    }




    public void generateNewQuestion(int lastPositionClicked) {
        Random rand = new Random();
        int firstNum = rand.nextInt(99);
        int secondNum = rand.nextInt(99);

        sumTextView.setText(Integer.toString(firstNum) + " + " + Integer.toString(secondNum));

        locationOfCorrectAnswer = rand.nextInt(4);

        lastClicks.add(counter%4, lastPositionClicked);
        counter++;

        if(counter >= 3) {
            if(lastClicks.get(0) == lastClicks.get(1) && lastClicks.get(1) == lastClicks.get(2)&&
                    lastClicks.get(2) == lastClicks.get(3)) {
                while(lastPositionClicked == locationOfCorrectAnswer) {
                    locationOfCorrectAnswer = rand.nextInt(4);
                }
            }
        }

        for(int i=0; i<4; i++) {
            if(i == locationOfCorrectAnswer) {
                answers.add(i, firstNum + secondNum);
            }
            else {
                //int incorrectAnswer = rand.nextInt(199);
                int incorrectAnswer = generateAnAnswer(firstNum + secondNum);

                while(incorrectAnswer == firstNum + secondNum) {
                    //incorrectAnswer = rand.nextInt(199);
                    incorrectAnswer = generateAnAnswer(firstNum + secondNum);
                }

                answers.add(i, incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }




    public void chooseAnswer(View view) {
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            score++;
            resultTextView.setText("Correct!");
        }
        else {
            resultTextView.setText("Wrong!");
        }

        numOfQuestions++;

        if(score < 10) {
            scoreStr = "0" + Integer.toString(score);
        }
        else {
            scoreStr = Integer.toString(score);
        }

        if(numOfQuestions < 10) {
            numOfQuestionsStr = "0" + Integer.toString(numOfQuestions);
        }
        else {
            numOfQuestionsStr = Integer.toString(numOfQuestions);
        }

        pointsTextView.setText(scoreStr + "/" + numOfQuestionsStr);

        generateNewQuestion(Integer.parseInt(view.getTag().toString()));
    }




    public void updateTimer(int secondsLeft) {
        int minutes = (int) (secondsLeft/60000);
        int seconds = (secondsLeft - (minutes * 60000))/1000;
        int milliseconds = (secondsLeft - (minutes * 60000) - (seconds*1000));

        if(seconds < 10) {
            if(milliseconds < 10) {
                timeTextView.setText("0" + Integer.toString(seconds) + ".00" + Integer.toString(milliseconds));
            }
            else if(milliseconds < 100) {
                timeTextView.setText("0" + Integer.toString(seconds) + ".0" + Integer.toString(milliseconds));
            }
            else {
                timeTextView.setText("0" + Integer.toString(seconds) + "." + Integer.toString(milliseconds));
            }
        }
        else {
            if(milliseconds < 10) {
                timeTextView.setText(Integer.toString(seconds) + ".00" + Integer.toString(milliseconds));
            }
            else if(milliseconds < 100) {
                timeTextView.setText(Integer.toString(seconds) + ".0" + Integer.toString(milliseconds));
            }
            else {
                timeTextView.setText(Integer.toString(seconds) + "." + Integer.toString(milliseconds));
            }
        }
    }




    public void updateProgressBar(int milliSecondsLeft) {
        progressBar.setProgress((totalSeconds*1000) - milliSecondsLeft);
    }




    public void resetProgressBar() {
        progressBar.setMax(totalSeconds*1000);
        progressBar.setProgress(0);
    }




    public void displayPlayAgain() {
        button0.setVisibility(View.INVISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);
        pointsTextView.setVisibility(View.INVISIBLE);
        timeTextView.setVisibility(View.INVISIBLE);
        sumTextView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        playAgainMenu.setVisibility(View.VISIBLE);

        finalScoreTextView.setText("Your Score: " + scoreStr + "/" + numOfQuestionsStr);

        String accuracyStr;
        if(score == 0 || numOfQuestions == 0) {
            accuracyStr = "0%";
        }
        else {
            accuracyStr = String.format("%.0f", ((((double)score)/((double)numOfQuestions))*100)) + "%";
        }
        accuracyTextView.setText("Accuracy: " + accuracyStr);
    }




    public void runCountDown() {
        CountDownTimer countDownTimer = new CountDownTimer(totalSeconds * 1000 + 500, 50) {
            @Override
            public void onTick(long l) {
                if(l < totalSeconds*1000) {
                    updateTimer((int) l);
                    updateProgressBar((int) l);
                }
                else {
                    updateTimer((int) totalSeconds*1000);
                    updateProgressBar((int) l);
                }
            }

            @Override
            public void onFinish() {
                timeTextView.setText("00.000");
                updateProgressBar(0);
                displayPlayAgain();
            }
        }.start();
    }




    public void playAgain(View view) {
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
        pointsTextView.setVisibility(View.VISIBLE);
        timeTextView.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        playAgainMenu.setVisibility(View.INVISIBLE);

        resultTextView.setText("");
        pointsTextView.setText("00/00");
        timeTextView.setText("30.000");

        score = 0;
        numOfQuestions = 0;

        resetProgressBar();
        generateNewQuestion(-1);
        runCountDown();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        timeTextView = (TextView) findViewById(R.id.timerTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        finalScoreTextView = (TextView) findViewById(R.id.finalScoreTextView);
        accuracyTextView = (TextView) findViewById(R.id.accuracyTextView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        playAgainMenu = (LinearLayout) findViewById(R.id.playAgainMenu);
        playAgainMenu.setVisibility(View.INVISIBLE);

        scoreStr = "0";
        numOfQuestionsStr = "0";

        resetProgressBar();
        generateNewQuestion(-1);
        runCountDown();
    }
}
