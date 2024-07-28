package com.example.racketreadyapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int selectedUserId;
    int matchupUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnDisplay = findViewById(R.id.viewRequestedMatchbtn);
        Button btnMatchUp = findViewById(R.id.viewMatchbtn);
        int userID;
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getInt("userid",0);
        SharedPreferences sharedPreferences1 = getSharedPreferences("score", MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getSharedPreferences("matchproposal", MODE_PRIVATE);


        matchupUser = sharedPreferences1.getInt("userID", 0); // get from MatchProposal
        selectedUserId = sharedPreferences2.getInt("selectedUserid", 0); // get from MatchDetails


        String selectCourt = sharedPreferences1.getString("selectedCourt","");
        String selectType = sharedPreferences1.getString("selectedType","");
        String selectTime = sharedPreferences1.getString("selectedTime","");

        Log.d("userID, proposeduser", "UserID is" + userID + "and selectedUserID is " + selectedUserId + "and matchUpUser is " + matchupUser);



        ListView listNavigation = findViewById(R.id.listNavigation);
        ArrayAdapter adapter;
        ArrayList<String> options = new ArrayList<>();
        options.add("Search For a Match");
        options.add("View User Profile");
        options.add("View Notifications");
        options.add("View Match History");
        options.add("Logout");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        listNavigation.setAdapter(adapter);
        listNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                switch(pos){
                    case 0:
                        startActivity(new Intent(MainActivity.this, FindMatchFilters.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, UserProfile.class));
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        prefs.edit().clear().apply();
                        startActivity(new Intent(MainActivity.this, Login.class));
                        Toast.makeText(MainActivity.this, "Logout Successful", Toast.LENGTH_LONG).show();
                }
            }
        });
        if (selectedUserId == userID) {
        btnDisplay.setVisibility(View.VISIBLE);
        }
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, ViewMatchProposal.class));
            }
        });
        if (matchupUser > 0){
            btnMatchUp.setVisibility(View.VISIBLE);
        }
        btnMatchUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScoreCard.class));
            }
        });

    }
}