package com.example.racketreadyapplication;

import static com.example.racketreadyapplication.UserRegistration.containsNumbers;
import static com.example.racketreadyapplication.UserRegistration.containsSpecialCharacters;
import static com.example.racketreadyapplication.UserRegistration.isValidEmail;
import static com.example.racketreadyapplication.UserRegistration3.isValidDate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        DatabaseHelper db = new DatabaseHelper(this);
        EditText fName = findViewById(R.id.edtUserFName);
        EditText lName = findViewById(R.id.edtUserLName);
        EditText phone = findViewById(R.id.edtUserPhone);
        EditText edtDob = findViewById(R.id.edtUserDate);
        Button btnApply = findViewById(R.id.btnProfileApply);
        Button btnBack = findViewById(R.id.btnEditProfBack);
        int userID = sharedPreferences.getInt("userid", 0);
        Cursor c = db.getUser(userID);
        String fullName = "";
        while (c.moveToNext()){
            fullName = c.getString(1);
            String[] split = fullName.split(" ");
            fName.setText(split[0]);
            lName.setText(split[1]);;
            phone.setText(c.getString(3));
            edtDob.setText(c.getString(5));
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this, Settings.class));
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ArrayList<String> flags = new ArrayList<>();
                String firstName = fName.getText().toString();
                if (containsNumbers(firstName) || containsSpecialCharacters(firstName) || firstName.contains(" ")){
                    flags.add("Your first name should not have numbers, special characters or spaces!");
                }
                if (firstName.isEmpty()){
                    flags.add("Please enter a First Name!");
                }
                String lastName = lName.getText().toString();
                if (containsNumbers(lastName) || containsSpecialCharacters(lastName) || firstName.contains(" ")){
                    flags.add("Your last name should not have numbers, special characters or spaces!");
                }
                if (lastName.isEmpty()){
                    flags.add("Please enter a First Name!");
                }
                // Concat the first and last name together for a full name, to be stored in the database
                String fullName = firstName + " " + lastName;
                // Phone checks
                String userPhone = phone.getText().toString();
                if (userPhone.length() > 10){
                    flags.add("Phone Number is too long!");
                }
                if (userPhone.length() < 10){
                    flags.add("Phone Number is too short!");
                }
                String dateFormat = "dd/MM/yyyy";
                String dob = "";
                String dobTest = edtDob.getText().toString();
                if (isValidDate(dobTest, dateFormat)){
                    dob = dobTest;
                }
                else{
                    flags.add("Please add a valid DOB (MM/DD/YYYY)");
                }
                if (flags.isEmpty()){
                    db.updateUserCreds(userID,fullName, userPhone, dob);
                    startActivity(new Intent(EditProfile.this, UserProfile.class));
                }else{
                    for (String flag : flags){
                        Toast.makeText(EditProfile.this, flag, Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isValidDate(String dateStr, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}