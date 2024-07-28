package com.example.racketreadyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FindMatchFilters extends AppCompatActivity {

    String skillLevel;
    String preferredDay;
    String location;

    int match_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_match_filters);


        //Josh Code Below
        int userID;
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getInt("userid",0);
        //Josh Code Above

        RadioGroup selectMatchType = findViewById(R.id.radioGroup);
        Button findMatch = findViewById(R.id.filterSearchMatchbtn);


        DatabaseHelper db = new DatabaseHelper(this);

        Cursor c = db.getUserId(userID);
        while (c.moveToNext()) {
            skillLevel = c.getString(c.getColumnIndexOrThrow("skillLevel"));
            location = c.getString(c.getColumnIndexOrThrow("location"));
            preferredDay = c.getString(c.getColumnIndexOrThrow("preferredDays"));
        }
        c.close();




        findMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("filterMatches", MODE_PRIVATE);
                SharedPreferences.Editor ed = preferences.edit();
                // ed.clear();
                int selected = selectMatchType.getCheckedRadioButtonId();
                if(selected == R.id.radioButtonSingles) {
                    match_type = 1;
                } else if (selected == R.id.radioButtonDoubles) {
                    match_type = 2;
                }

                ed.putString("skilllevel", skillLevel);
                ed.putString("preferredDay", preferredDay);
                ed.putString("location", location);
                ed.putInt("match_type", match_type);
                ed.apply();

                Log.d("pre-location", "Pre-searchMatches " + location);
                Log.d("pre-preferredDay", "Pre-preferredDays " + preferredDay);
                Log.d("pre-skillLevel", "Pre-skillLevel " + skillLevel);
                Log.d("pre-UserId", "User ID is " + userID);


                startActivity(new Intent(FindMatchFilters.this, searchMatches.class));
            }
        });
    }
}