package com.example.racketreadyapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class EditPrefs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_prefs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        DatabaseHelper db = new DatabaseHelper(this);
        SharedPreferences preferences = getSharedPreferences("filterMatches", MODE_PRIVATE);
        int userID = preferences.getInt("userid",0);
        Spinner spinMatchType = findViewById(R.id.spinEditMatchType);
        Spinner spinLocation = findViewById(R.id.spinEditLocation);
        ListView listTimes = findViewById(R.id.listEditTime);
        ListView listDays = findViewById(R.id.listEditDays);
        Cursor c = db.getUser(userID);
        ArrayList<String> currentDays = new ArrayList<>();
        ArrayList<String> currentTimes = new ArrayList<>();
        TextView tvDays = findViewById(R.id.tvEditPrefDays);
        Button btnBack = findViewById(R.id.btnEditMatchesBack);
        Button btnApply = findViewById(R.id.btnMatchApply);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPrefs.this, Settings.class));
            }
        });

        int currentMatchType = 0;
        String currentLocation = "";
        while(c.moveToNext()){
            String[] dbDays = c.getString(7).split(", ");
            currentDays.addAll(Arrays.asList(dbDays));
            String[] dbTimes = c.getString(8).split(", ");
            currentTimes.addAll(Arrays.asList(dbTimes));
            currentLocation = c.getString(10);
            currentMatchType = c.getInt(11);
        }
        ArrayList<String> dayList = new ArrayList<>();
        dayList.add("Monday");
        dayList.add("Tuesday");
        dayList.add("Wednesday");
        dayList.add("Thursday");
        dayList.add("Friday");
        dayList.add("Saturday");
        dayList.add("Sunday");
        ArrayAdapter<String> dayAdapter;
        dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dayList);
        listDays.setAdapter(dayAdapter);
        for (String cDay : currentDays){
            int pos = dayList.indexOf(cDay);
            if (pos != -1){
                listDays.setItemChecked(pos,true);
            }
        }

        ArrayList<String> times = new ArrayList<>();
        times.add("Morning");
        times.add("Afternoon");
        times.add("Evening");
        ArrayAdapter<String> timeAdapter;
        timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, times);
        listTimes.setAdapter(timeAdapter);
        for (int i = 0; i < currentTimes.size(); i++){
            int pos = times.indexOf(currentTimes.get(i));
            if (pos != -1){
                listTimes.setItemChecked(pos,true);
            }
        }

        ArrayList<String> matches = new ArrayList<>();
        matches.add("Singles");
        matches.add("Doubles");
        ArrayAdapter<String> matchAdapter;
        matchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, matches);
        spinMatchType.setAdapter(matchAdapter);
        if (currentMatchType == 1){
            spinMatchType.setSelection(0);
        }else {
            spinMatchType.setSelection(1);
        }

        ArrayList<String> locations = new ArrayList<>();
        locations.add("British Columbia");
        locations.add("Alberta");
        locations.add("Saskatchewan");
        locations.add("Manitoba");
        locations.add("Ontario");
        locations.add("Quebec");
        locations.add("Nova Scotia");
        locations.add("New Brunswick");
        locations.add("Prince George Island");
        locations.add("Yukon");
        locations.add("Northwest Territories");
        locations.add("Nunavut");
        ArrayAdapter<String> locationAdapter;
        locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        spinLocation.setAdapter(locationAdapter);
        for (String location : locations){
            if (currentLocation.equals(location)){
                spinLocation.setSelection(locations.indexOf(location));
            }
        }

        StringBuilder selectedTimes= new StringBuilder();
        listTimes.setOnItemClickListener(((parent, view, position, id) -> {
            selectedTimes.setLength(0);
            for (int i =0; i < listTimes.getCount(); i++){
                if(listTimes.isItemChecked(i)){
                    selectedTimes.append(times.get(i));
                    if (selectedTimes.length() > 1){
                        selectedTimes.append(", ");
                    }
                }
            }
        }));

        StringBuilder selectedDays= new StringBuilder();
        listDays.setOnItemClickListener(((parent, view, position, id) -> {
            selectedDays.setLength(0);
            for (int i =0; i < listDays.getCount(); i++){
                if(listDays.isItemChecked(i)){
                    selectedDays.append(dayList.get(i));
                    if (selectedDays.length()>1){
                        selectedDays.append(", ");
                    }
                }
            }
        }));

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newLocation = spinLocation.getSelectedItem().toString();
                String newMatchType = spinMatchType.getSelectedItem().toString();
                int matchType = 0;
                if (newMatchType.equals("Singles")){
                    matchType = 1;
                }else{
                    matchType = 2;
                }
                boolean u = db.updateMatchPreferences(userID,selectedDays.toString(),selectedTimes.toString(),newLocation,matchType);
                if (u){
                    Toast.makeText(EditPrefs.this, "Updated Successfully!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditPrefs.this, UserProfile.class));
                }else{
                    Toast.makeText(EditPrefs.this, "Error with update!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}