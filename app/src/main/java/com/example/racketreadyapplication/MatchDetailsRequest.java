package com.example.racketreadyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MatchDetailsRequest extends AppCompatActivity {


    int selectUserID;
    String selectCourt;
    String selectTime;
    String selectType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details_request);
        int userID;
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getInt("userid",0);
//        final static String Table6Name = "MatchTable";
//        final static String Table6Col1 = "matchID";
//        final static String Table6Col2 = "typeID";
//        final static String Table6Col3 = "courtID";
//        final static String Table6Col4 = "score"; // Can be stored as side1/side2. (Ex. 11/4, side1 wins). Or whatever way anyone wants to store it
//        final static String Table6Col5 = "isTournament";
//        final static String Table6Col6 = "isCompetitive";


        Spinner spinner = (Spinner) findViewById(R.id.selectCourtSpinner);
        RadioGroup timeGroup = findViewById(R.id.timeRadio);
        RadioButton radioMorning = findViewById(R.id.morningRadio);
        RadioButton radioAfternoon = findViewById(R.id.afternoonRadio);

        RadioGroup compFriendlyGroup = findViewById(R.id.compFriendlyRadio);
        RadioButton radioFriendly = findViewById(R.id.FriendlyRadio);
        RadioButton radioCompetitive = findViewById(R.id.competitveRadio);


        CheckBox policy = findViewById(R.id.policyCheckBox);

        TextView txtselectCourt = findViewById(R.id.selectCourttxt);
        TextView txtTime = findViewById(R.id.timeTxt);
        TextView matchType = findViewById(R.id.matchTypetxt);
        EditText ad_comments = findViewById(R.id.editTxtadComment);
        TextView ad_comment_txt = findViewById(R.id.adCommentTxt);
        TextView title = findViewById(R.id.fillMatchTxt);
        Button btn = findViewById(R.id.sendMatchRequestBtn);
        DatabaseHelper db = new DatabaseHelper(this);

        selectUserID = getIntent().getIntExtra("selectedUser", -1);

        //*
        //
        //    <item>Kennedy Tennis Courts</item>
        //        <item>Sunshine Hills Tennis Club</item>
        //        <item>RacketReady Tennis Court</item>
        //
        //
        // **

//        String email = getIntent().getStringExtra("user-email");
//        String password = getIntent().getStringExtra("user-password");

//        db.getUserId(email, password);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.courtNames,
                android.R.layout.simple_spinner_item
        );

adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinner.setAdapter(adapter);

selectCourt = spinner.getSelectedItem().toString();




    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(policy.isChecked()){

                SharedPreferences sharedPreferences = getSharedPreferences("matchproposal", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

//                Intent intent = new Intent(MatchDetailsRequest.this, MainActivity.class);

                int selectedtype = compFriendlyGroup.getCheckedRadioButtonId();

                if(selectedtype == R.id.competitveRadio) {
                    selectType = "Competitive";
                }
                else if(selectedtype == R.id.FriendlyRadio) {
                    selectType = "Friendly";
                }

                int selectedtime = timeGroup.getCheckedRadioButtonId();

                if(selectedtime == R.id.morningRadio){
                    selectTime = "Morning";
                }
                else if(selectedtime == R.id.afternoonRadio){
                    selectTime = "Afternoon";
                }


                editor.putInt("selectedUserid", selectUserID);
                editor.putString("selectedCourt", selectCourt);
                editor.putString("selectedType", selectType);
                editor.putString("selectedTime", selectTime);
                editor.putInt("sendingUser", userID);
                editor.apply();

                Toast.makeText(MatchDetailsRequest.this, "Match Proposal Sent to Selected User!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MatchDetailsRequest.this, MainActivity.class));
            }
            else {
                Toast.makeText(MatchDetailsRequest.this, "Please Agree to Terms and Policy", Toast.LENGTH_SHORT).show();
            }
        }
    });
    }

}