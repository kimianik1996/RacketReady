package com.example.racketreadyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewMatchProposal extends AppCompatActivity {

    private ImageView imageView3;
    private TextView textviewProName;
    private TextView proposedSkillLevel;
    private TextView textviewLevelPro;
    private EditText editTextProName;
    private EditText editTxtProSkillLevel;
    private EditText editTextproLevel;
    private TextView textviewProCourt;
    private EditText editTextProCourt;
    private TextView textviewProTime;
    private EditText edittextproTime;
    private TextView textviewProMatchType;
    private EditText editTextMatchType;
    private TextView textviewAddComm;
    private EditText editTxtAddComments;
    private Button rejectMatchBtn;
    private Button acceptMatchbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match_proposal);

        int userID;
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getInt("userid",0);

        DatabaseHelper db = new DatabaseHelper(this);

        imageView3 = findViewById(R.id.imageView3);

        textviewProName = findViewById(R.id.edittextProName);
        proposedSkillLevel = findViewById(R.id.proposedSkillLevel);
        textviewLevelPro = findViewById(R.id.textviewLevelPro);
        textviewProCourt = findViewById(R.id.textviewProCourt);
        textviewProTime = findViewById(R.id.textviewProTime);
        textviewProMatchType = findViewById(R.id.textviewProMatchType);
        textviewAddComm = findViewById(R.id.textviewAddComm);


        editTextProName = findViewById(R.id.editTextProName);
        editTxtProSkillLevel = findViewById(R.id.editTxtProSkillLevel);
        editTextproLevel = findViewById(R.id.editTextproLevel);
        editTextProCourt = findViewById(R.id.editTextProCourt);
        edittextproTime = findViewById(R.id.edittextproTime);
        editTextMatchType = findViewById(R.id.editTextMatchType);
        editTxtAddComments = findViewById(R.id.editTxtAddComments);

        rejectMatchBtn = findViewById(R.id.rejectMatchBtn);
        acceptMatchbtn = findViewById(R.id.acceptMatchbtn);


        SharedPreferences matchProposalPrefs = getSharedPreferences("matchproposal", MODE_PRIVATE);
        int sendingUserID = matchProposalPrefs.getInt("sendingUser", 0);

        getSendingUser(sendingUserID);

        editTextProCourt.setText(matchProposalPrefs.getString("selectedCourt",""));
        edittextproTime.setText(matchProposalPrefs.getString("selectedTime",""));
        editTextproLevel.setText(matchProposalPrefs.getString("selectedType", ""));


        rejectMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = matchProposalPrefs.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(ViewMatchProposal.this, "Proposal Rejected, Better Luck Next Time!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ViewMatchProposal.this, MainActivity.class));
            }
        });

        acceptMatchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = matchProposalPrefs.edit();
                //   SharedPreferences sharedPreferences1 = getSharedPreferences("matchproposal", MODE_PRIVATE);
                SharedPreferences matchup = getSharedPreferences("score", MODE_PRIVATE);

//                final static String Table6Name = "MatchTable";
//                final static String Table6Col1 = "matchID";
//                final static String Table6Col2 = "typeID";
//                final static String Table6Col3 = "courtID";
//                final static String Table6Col4 = "score"; // Can be stored as side1/side2. (Ex. 11/4, side1 wins). Or whatever way anyone wants to store it
//                final static String Table6Col5 = "isTournament";
//                final static String Table6Col6 = "isCompetitive";

                String name = String.valueOf(editTextProName.getText());
                String matchType = String.valueOf(editTextMatchType.getText());
                String court = String.valueOf(editTextProCourt.getText());
                String matchLevelType = String.valueOf(editTextproLevel.getText());

                SharedPreferences.Editor add_match = matchup.edit();
                add_match.putInt("sendingUserID", sendingUserID);
                add_match.putInt("userID", userID);
                add_match.putString("proName", name);
                add_match.putString("matchType", matchType);
                add_match.putString("courtType", court);
                add_match.putString("matchLevelType", matchLevelType);

                editor.clear();

                add_match.apply();


                startActivity(new Intent(ViewMatchProposal.this, MainActivity.class));
                Toast.makeText(ViewMatchProposal.this, "Match Has been Schedule, Have Fun!", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getSendingUser(int sendingUserID) {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor c = db.getUserId(sendingUserID);
        try {
            while (c.moveToNext()) {
                String fullName = c.getString(c.getColumnIndexOrThrow("fullname"));
                String skillLevel = c.getString(c.getColumnIndexOrThrow("skillLevel"));
                int matchTypeId = c.getInt(c.getColumnIndexOrThrow("matchTypeID"));

                editTextProName.setText(fullName);
                editTxtProSkillLevel.setText(skillLevel);
                if(matchTypeId == 1) {

                    editTextMatchType.setText("Singles");
                }
                else {
                    editTextMatchType.setText("Doubles");
                }
            }
        } finally {
            c.close();
        }
    }


    }

