package com.example.courtcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int score_a = 0;
    int score_b = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addThreeTeamA(View view) {
        score_a = score_a+3;
        displayForTeamA(score_a);
    }

    public void addTwoTeamA(View view) {
        score_a = score_a+2;
        displayForTeamA(score_a);
    }

    public void freeThrowA(View view) {
        score_a = score_a+1;
        displayForTeamA(score_a);
    }

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

     //////////// for Team B /////////////

    public void addThreeTeamB(View view) {
        score_b = score_b+3;
        displayForTeamB(score_b);
    }

    public void addTwoTeamB(View view) {
        score_b = score_b+2;
        displayForTeamB(score_b);
    }

    public void freeThrowB(View view) {
        score_b = score_b+1;
        displayForTeamB(score_b);
    }

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    // Reset the whole score of both teams
    public void reset(View view) {
        score_a = 0;
        score_b = 0;
        displayForTeamA(score_a);
        displayForTeamB(score_b);
    }
}
