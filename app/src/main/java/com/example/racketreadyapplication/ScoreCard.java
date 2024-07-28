package com.example.racketreadyapplication;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class ScoreCard extends AppCompatActivity {

    private TextView scoreProName;
    private TextView scoreCourt;
    private TextView scoreMatchType;
    private TextView scoreMatchLevel;
    private EditText editTextScoreName;
    private EditText editTextScoreCourt;
    private EditText editTextScoreMatchType;
    private EditText editTextScoreMatchLevel;
    private TextView scoreSetsPlayed;
    private RadioGroup scoreRadioGroup;
    private RadioButton score2Sets;
    private RadioButton score3Sets;
    private RadioButton score4Sets;
    private TextView txtViewYourScore;
    private EditText editTextSetsWon;
    private TextView textViewOpponentScore;
    private EditText editTextOPPScore;
    private Button confirmScoreBtn;
    private Button reviseScoreBtn;

    private int userID;

    private int matchUpUserID;
    private int sendingUserID;

    int setScoreLimit = 0;

    int OwnScore;

    int OpponentScore;

    private CheckBox scoreCheckBox;

    boolean reviseStatus = false;

    private int defaultscore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getInt("userid",0); // this is whoever's account/user you are using
        SharedPreferences matchup = getSharedPreferences("score", MODE_PRIVATE);
        SharedPreferences proposal = getSharedPreferences("matchproposal", MODE_PRIVATE);
        matchUpUserID = matchup.getInt("userID", 0); // this is the User who sent the proposal
        sendingUserID = matchup.getInt("sendingUserID", 0); // this will be the User that you sent the proposal too
        reviseStatus = matchup.getBoolean("reviseStatus", false);

        DatabaseHelper db = new DatabaseHelper(this);

//         add_match.putInt("sendingUserID", sendingUserID);
//                add_match.putInt("userID", userID);
//                add_match.putString("proName", name);
//                add_match.putString("matchType", matchType);
//                add_match.putString("courtType", court);
//                add_match.putString("matchLevelType", matchLevelType);

        scoreProName = findViewById(R.id.scoreProName);
        scoreCourt = findViewById(R.id.scoreCourt);
        scoreMatchType = findViewById(R.id.scoreMatchType);
        scoreMatchLevel = findViewById(R.id.scoreMatchLevel);
        editTextScoreName = findViewById(R.id.editTextScoreName);
        editTextScoreCourt = findViewById(R.id.editTextScoreCourt);
        editTextScoreMatchType = findViewById(R.id.editTextScoreMatchType);
        editTextScoreMatchLevel = findViewById(R.id.editTextScoreMatchLevel);
        scoreSetsPlayed = findViewById(R.id.scoreSetsPlayed);
        scoreRadioGroup = findViewById(R.id.scoreRadioGroup);
        score2Sets = findViewById(R.id.score2Sets);
        score3Sets = findViewById(R.id.score3Sets);
        score4Sets = findViewById(R.id.score4Sets);
        txtViewYourScore = findViewById(R.id.txtViewYourScore);
        editTextSetsWon = findViewById(R.id.editTextSetsWon);
        textViewOpponentScore = findViewById(R.id.textViewOpponentsScore);
        editTextOPPScore = findViewById(R.id.editTextOPPScore);
        confirmScoreBtn = findViewById(R.id.ConfirmScorebtn);
        reviseScoreBtn = findViewById(R.id.ReviseScorebtn);
        scoreCheckBox = findViewById(R.id.checkBox);

        if(userID == sendingUserID) {
            displayUsersProposal(matchUpUserID);
        }
        else {
            editTextScoreName.setText(matchup.getString("proName", ""));
        }
        editTextScoreCourt.setText(matchup.getString("courtType", ""));
        editTextScoreMatchType.setText(matchup.getString("matchType",""));
        editTextScoreMatchLevel.setText(matchup.getString("matchLevelType",""));

if(reviseStatus){
    int numberSetsFixed = matchup.getInt("numberOfSets", 0);
     OpponentScore = matchup.getInt("OwntoOppScore", 0);
    OwnScore = matchup.getInt("OpptoOwnScore", 0);

    String oppScoreString = String.valueOf(OpponentScore);
    String ownScoreString = String.valueOf(OwnScore);

    editTextSetsWon.setText(ownScoreString);
    editTextOPPScore.setText(oppScoreString);

    if(numberSetsFixed == 2){
        score2Sets.setChecked(true);
    } else if (numberSetsFixed ==3) {
        score3Sets.setChecked(true);
    } else if (numberSetsFixed ==4) {
        score4Sets.setChecked(true);
    }


    fixedValuesRevision(reviseStatus);

} else {
    editTextSetsWon.setText("0");
    editTextOPPScore.setText("0");
}


        scoreCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
         editTextOPPScore.setEnabled(!isChecked);
         editTextSetsWon.setEnabled(!isChecked);
         score2Sets.setEnabled(!isChecked);
         score3Sets.setEnabled(!isChecked);
         score4Sets.setEnabled(!isChecked);

            }
        });

                confirmScoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        reviseStatus = false;
                        int Courtid = -1;
                        int isTournament = 0;
                        int isMatchType;
                        int typeID = -1;
                        OwnScore = Integer.parseInt(editTextSetsWon.getText().toString());
                        OpponentScore = Integer.parseInt(editTextOPPScore.getText().toString());

                        String match_type = String.valueOf(editTextScoreMatchType.getText());
                        String MatchLevel = String.valueOf(editTextScoreMatchLevel.getText());
                        String court = String.valueOf(editTextScoreCourt.getText());
                        String win_lose_standing;
                        int matchID = OwnScore + OpponentScore;

                        if (match_type.equals("Singles")) {
                            typeID = 1;
                        } else if (match_type.equals("Doubles")) {
                            typeID = 2;
                        }


                        if (court.equals("Kennedy Tennis Courts")) {
                            Courtid = 0;
                        } else if (court.equals("Sunshine Hills Tennis Club")) {
                            Courtid = 1;
                        } else if (court.equals("RacketReady Tennis Court")) {
                            Courtid = 2;
                        }
                        String scoreString = OwnScore + "/" + OpponentScore;
                        if (MatchLevel.equals("Competitive")) {
                            isMatchType = 1;
                        } else {
                            isMatchType = 0;
                        }
                        if (OwnScore > OpponentScore) {
                            win_lose_standing = "Win";
                        } else {
                            win_lose_standing = "Lose";
                        }
                        if (!scoreCheckBox.isChecked()) {
                            Toast.makeText(ScoreCard.this, "Please Confirm Score With The Checkbox", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean addMatch = db.addMatch(typeID, Courtid, scoreString, isTournament, isMatchType);
                            boolean addMatchHistory = db.addMatchHistory(userID, matchID, win_lose_standing, null);

                            if (userID != matchUpUserID) {
                                if (win_lose_standing.equals("Win")) {
                                    win_lose_standing = "Lose";
                                } else {
                                    win_lose_standing = "Win";
                                }
                                addMatchHistory = db.addMatchHistory(matchUpUserID, matchID, win_lose_standing, null);
                            } else {
                                if (win_lose_standing.equals("Win")) {
                                    win_lose_standing = "Lose";
                                } else {
                                    win_lose_standing = "Win";
                                }
                                addMatchHistory = db.addMatchHistory(sendingUserID, matchID, win_lose_standing, null);
                            }
                            if (addMatch && addMatchHistory) {
                                SharedPreferences.Editor editor = matchup.edit();
                                SharedPreferences.Editor editor2 = proposal.edit();
                                editor.clear();
                                editor2.clear();

                                editor.apply();
                                editor2.apply();

                                Toast.makeText(ScoreCard.this, "Match Has Been Completed! Hope You Had Fun", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ScoreCard.this, MainActivity.class));
                            } else {
                                Toast.makeText(ScoreCard.this, "You Done Messed Up", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });



                reviseScoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean temp_reviseStatus = false;
                        fixedValuesRevision(temp_reviseStatus);

                        if(reviseStatus){
                            Toast.makeText(ScoreCard.this, "You Can Now Apply Changes", Toast.LENGTH_SHORT).show();
                        }

                        int score = parseInt(String.valueOf(scoreRadioGroup.getCheckedRadioButtonId()));
//
                        if (score == R.id.score2Sets) {
                            setScoreLimit = 2;
                        } else if (score == R.id.score3Sets) {
                            setScoreLimit = 3;
                        } else if (score == R.id.score4Sets) {
                            setScoreLimit = 4;
                        }


                        OwnScore = Integer.parseInt(editTextSetsWon.getText().toString());
                        OpponentScore = Integer.parseInt(editTextOPPScore.getText().toString());

                        if (!scoreCheckBox.isChecked()) {
                            Toast.makeText(ScoreCard.this, "Please Confirm Score With The Checkbox", Toast.LENGTH_SHORT).show();
                        } else if (OwnScore + OpponentScore == 0 || setScoreLimit <= 0) {
                            Toast.makeText(ScoreCard.this, "Please Enter the Score or Set", Toast.LENGTH_SHORT).show();
                        } else {
                            if (OwnScore > setScoreLimit || OwnScore < 0 || OpponentScore > setScoreLimit || OpponentScore < 0 || OwnScore + OpponentScore != setScoreLimit) {
                                Toast.makeText(ScoreCard.this, "Invalid Score, Please Try Again", Toast.LENGTH_SHORT).show();
                            } else {
                                reviseStatus = true;
                                SharedPreferences.Editor editor = matchup.edit();
                                editor.putBoolean("reviseStatus", reviseStatus);
                                editor.putInt("numberOfSets", setScoreLimit);
                                editor.putInt("OwntoOppScore", OwnScore);
                                editor.putInt("OpptoOwnScore", OpponentScore);
                                editor.apply();
                                Toast.makeText(ScoreCard.this, "Your Revision Has Been Sent to Your Opponent for Correction!", Toast.LENGTH_SHORT).show();
                                Log.d("loggedScores", "status is " + reviseStatus + "number of sets are " + setScoreLimit + "Own Score is " + OwnScore + "Opponent Score is " + OpponentScore);
                                startActivity(new Intent(ScoreCard.this, MainActivity.class));
                            }
                        }


                    }
                });
    }
    private void displayUsersProposal(int userID) {
        DatabaseHelper db = new DatabaseHelper(this);
        try (Cursor c = db.getUserId(userID)) {
            while (c.moveToNext()) {
                String fullName = c.getString(c.getColumnIndexOrThrow("fullname"));
                editTextScoreName.setText(fullName);
                c.close();
            }
        }

    }
    public void fixedValuesRevision(boolean b){
        if(b){
            editTextOPPScore.setEnabled(false);
            editTextSetsWon.setEnabled(false);
            score2Sets.setEnabled(false);
            score3Sets.setEnabled(false);
            score4Sets.setEnabled(false);
        } else {
            editTextOPPScore.setEnabled(true);
            editTextSetsWon.setEnabled(true);
            score2Sets.setEnabled(true);
            score3Sets.setEnabled(true);
            score4Sets.setEnabled(true);
        }
    }

}