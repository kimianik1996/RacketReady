package com.example.racketreadyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class UserRegistration2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_registration2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String name = null;
        String email = null;
        String phone = null;
        String password = null;
        Intent intent = getIntent();

        if (intent!=null){
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            phone = intent.getStringExtra("phone");
            password = intent.getStringExtra("password");
        }
        ListView dayListView = findViewById(R.id.listDays);
        Spinner skillLevelSpin = findViewById(R.id.spinSkillLevel);
        Spinner matchLevelSpin = findViewById(R.id.spinMatchType);

        ArrayList<String> dayList = new ArrayList<>();
        dayList.add("Monday");
        dayList.add("Tuesday");
        dayList.add("Wednesday");
        dayList.add("Thursday");
        dayList.add("Friday");
        dayList.add("Saturday");
        dayList.add("Sunday");

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dayList);
        dayListView.setAdapter(adapter);

        Button btnNext = findViewById(R.id.btnRegNext2);
        Button btnBack = findViewById(R.id.btnReg2Back);

        StringBuilder selectedDays= new StringBuilder();
        dayListView.setOnItemClickListener(((parent, view, position, id) -> {
            selectedDays.setLength(0);
            for (int i =0; i < dayListView.getCount(); i++){
                if(dayListView.isItemChecked(i)){
                    selectedDays.append(dayList.get(i)).append(", ");
                }
            }
        }));
        String finalName = name;
        String finalEmail = email;
        String finalPhone = phone;
        String finalPassword = password;
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedLevel = skillLevelSpin.getSelectedItem().toString();
                ArrayList<String> flags = new ArrayList<>();
                if (selectedLevel.isEmpty()){
                    flags.add("Please put in a skill level!");
                }
                String selectedMatch = matchLevelSpin.getSelectedItem().toString();
                if (selectedMatch.isEmpty()){
                    flags.add("Please put in a match type");
                }
                String strSelectedDays = selectedDays.toString();
                if (strSelectedDays.isEmpty()){
                    flags.add("Please enter at least 1 day");
                }
                if (flags.isEmpty()){
                    Intent box = new Intent(UserRegistration2.this, UserRegistration3.class);
                    box.putExtra("name", finalName);
                    box.putExtra("email", finalEmail);
                    box.putExtra("phone", finalPhone);
                    box.putExtra("password", finalPassword);
                    box.putExtra("skillLevel", selectedLevel);
                    box.putExtra("matchType", selectedMatch);
                    box.putExtra("preferredDays", selectedDays.toString());
                    startActivity(box);
                }
                else{
                    for (String flag : flags){
                        Toast.makeText(UserRegistration2.this, flag, Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnBox = new Intent(UserRegistration2.this, UserRegistration.class);
                /* returnBox.putExtra("name", finalName);
                returnBox.putExtra("email", finalEmail);
                returnBox.putExtra("phone", finalPhone);
                returnBox.putExtra("password", finalPassword);*/
                startActivity(returnBox);
            }
        });
    }


}