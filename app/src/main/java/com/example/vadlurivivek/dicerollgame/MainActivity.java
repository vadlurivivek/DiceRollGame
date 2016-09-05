package com.example.vadlurivivek.dicerollgame;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;
import android.os.Handler;
import android.widget.Toast;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    TextView tvScore;
    TextView tvTurnScore;
    Button btReset;
    Button btHold;
    Button btRoll;
    ImageView ivDice;
    Integer userOverAllScore = 0;
    Integer computerOverAllScore = 0;
    Integer userTurnScore = 0;
    Integer computerTurnScore = 0;
    TextView tvFinalStatement;
    Boolean stopTurn = false;
    int TotalComputerTurnScore=0;
    long startTime = 0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            computerTurn();
            if(stopTurn) {
                timerHandler.removeCallbacks(timerRunnable);
                stopTurn= false;
            }else{
                timerHandler.postDelayed(this, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView)findViewById(R.id.tvScore);
        tvTurnScore = (TextView)findViewById(R.id.tvTurnScore);
        btReset = (Button)findViewById(R.id.btReset);
        btHold = (Button)findViewById(R.id.btHold);
        btRoll = (Button)findViewById(R.id.btRoll);
        ivDice = (ImageView)findViewById(R.id.ivDice);
        tvFinalStatement = (TextView)findViewById(R.id.tvFinalStatement);
        tvScore.setText("Computer's score: "+computerOverAllScore+" | Your score: "+userOverAllScore );
        tvTurnScore.setText("Your Turn | score: " + userTurnScore);
        UserTurn();
    }

    public void UserTurn(){
        if(userOverAllScore < 100) {
            btRoll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int random = getRandomDiceNumber();
                    getDiceFace(random);
                    if (random == 1) {
                        btRoll.setEnabled(false);
                        btHold.setEnabled(false);
                        userTurnScore = 0;
                        Toast.makeText(MainActivity.this,"you got one",Toast.LENGTH_SHORT).show();
                        tvScore.setText("Computer's score: " + computerOverAllScore + " | Your score: " + userOverAllScore);
                        tvTurnScore.setText("Computer's Turn | score: " + computerTurnScore);
                        Log.d("asdf",""+random);
                        timerHandler.postDelayed(timerRunnable, 500);

                    } else {
                        userTurnScore += random;
                        tvTurnScore.setText("Your Turn | score: " + userTurnScore);
                    }
                }
            });

            btHold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    userOverAllScore = userOverAllScore + userTurnScore;
                    tvScore.setText("Computer's score: " + computerOverAllScore + " | Your score: " + userOverAllScore);
                    tvTurnScore.setText("Computer's Turn | score: " + computerTurnScore);
                    userTurnScore = 0;
                    timerHandler.postDelayed(timerRunnable, 500);
                    btRoll.setEnabled(false);
                    btHold.setEnabled(false);
                }
            });
            btReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reset();
                }
            });
        }else {
            btRoll.setEnabled(false);
            btHold.setEnabled(false);
            tvFinalStatement.setText("YOU WON");
            tvFinalStatement.setVisibility(View.VISIBLE);
        }
    }
    public int getRandomDiceNumber(){
        Random rdm = new Random();
        return rdm.nextInt(6)+1;
    }
    public void reset(){
        userOverAllScore = 0;
        computerOverAllScore = 0;
        userTurnScore = 0;
        computerTurnScore = 0;
        tvScore.setText("Computer's score: "+computerOverAllScore+" | Your score: "+userOverAllScore );
        tvTurnScore.setText("your Turn | score: " + userTurnScore);
        btRoll.setEnabled(true);
        btHold.setEnabled(true);
        tvFinalStatement.setVisibility(View.GONE);
    }


    public void getDiceFace(int random){
        switch (random){
            case 1:
                ivDice.setImageResource(R.drawable.dice1);
                break;
            case 2:
                ivDice.setImageResource(R.drawable.dice2);
                break;
            case 3:
                ivDice.setImageResource(R.drawable.dice3);
                break;
            case 4:
                ivDice.setImageResource(R.drawable.dice4);
                break;
            case 5:
                ivDice.setImageResource(R.drawable.dice5);
                break;
            case 6:
                ivDice.setImageResource(R.drawable.dice6);
                break;
        }
    }
    public void computerHold(){
        computerOverAllScore = computerOverAllScore + computerTurnScore;
        tvScore.setText("Computer's score: " + computerOverAllScore + " | Your score: " + userOverAllScore);
        tvTurnScore.setText("Your turn |  score: " + userTurnScore);
        computerTurnScore = 0;
        btRoll.setEnabled(true);
        btHold.setEnabled(true);
        stopTurn = true;
        UserTurn();
    }
    public void computerTurn() {
        int random = getRandomDiceNumber();
        getDiceFace(random);

        if (computerTurnScore + random < 20) {
            if (random == 1) {
                //Log.d("zxcv", "" + random);
                stopTurn = true;
                computerTurnScore = 0;
                Toast.makeText(MainActivity.this, "computer got one", Toast.LENGTH_LONG);
                tvTurnScore.setText("Your turn: " + userTurnScore);
                tvScore.setText("Computer's score: " + computerOverAllScore + " | Your score: " + userOverAllScore);
                btRoll.setEnabled(true);
                btHold.setEnabled(true);
                UserTurn();
            } else {
                computerTurnScore += random;
                //Log.d("qwer", "" + random);
                tvTurnScore.setText("Computer's Turn | score: " + computerTurnScore);
            }
        } else {
            computerHold();
        }
        if(computerOverAllScore > 100) {
            stopTurn = true;
            btRoll.setEnabled(false);
            btHold.setEnabled(false);
            tvFinalStatement.setText("YOU LOST");
            tvFinalStatement.setVisibility(View.VISIBLE);
        }
    }
}

//Drawable d = getResources().getDrawable(android.R.drawable.ic_dialog_email);
//ImageView image = (ImageView)findViewById(R.id.image);
//image.setImageDrawable(d);

//timerTextView.setText(String.format("%d:%02d", minutes, seconds));
/*
* else {
            btRoll.setEnabled(false);
            btHold.setEnabled(false);
            tvFinalStatement.setText("YOU LOST");
            tvFinalStatement.setVisibility(View.VISIBLE);
        }
* */