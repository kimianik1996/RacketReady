package com.example.racketreadyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize UI components and Database Helper
        Button btnNext = findViewById(R.id.btnRegNext);
        EditText fName = findViewById(R.id.edtFName);
        EditText lName = findViewById(R.id.edtLName);
        EditText email = findViewById(R.id.edtEmail);
        EditText phone = findViewById(R.id.edtPhone);
        EditText password = findViewById(R.id.edtPassword);
        EditText confirmPassword = findViewById(R.id.edtConfirmPassword);
        DatabaseHelper db = new DatabaseHelper(this);
/*
        Intent intent = getIntent();
        if (intent != null){
            String fullName = intent.getStringExtra("name");
            assert fullName != null;
            String[] breakName = fullName.split(",");
            fName.setText(breakName[0]);
            lName.setText(breakName[1]);
            email.setText(intent.getStringExtra("email"));
            phone.setText(intent.getStringExtra("phone"));
            password.setText(intent.getStringExtra("password"));
        }
*/
        // On Click for validating to move on to next step in registration
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Error flag arraylist that stores strings containing information about an error
                ArrayList<String> flags = new ArrayList<>();
                // First/Last name checks
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
                // Email checks
                String userEmail = email.getText().toString();
                if (db.checkEmails(userEmail)){
                    flags.add("Email already in use!");
                }
                if (!isValidEmail(userEmail)){
                    flags.add("Invalid Email Format");
                }
                if (userEmail.isEmpty()){
                    flags.add("Please enter an email!");
                }
                // Phone checks
                String userPhone = phone.getText().toString();
                if (userPhone.length() > 10){
                    flags.add("Phone Number is too long!");
                }
                if (userPhone.length() < 10){
                    flags.add("Phone Number is too short!");
                }
                //Password check
                if (password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()){
                    flags.add("Password is empty");
                }
                if (password.getText().toString().isBlank() || confirmPassword.getText().toString().isBlank()){
                    flags.add("Password contains blanks");
                }
                if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                    flags.add("Passwords do not match!");
                }
                // If there are no error flags, put the data in an intent and send it over to the next activity
                if (flags.isEmpty()){
                    Intent userData = new Intent(UserRegistration.this, UserRegistration2.class);
                    userData.putExtra("name", fullName);
                    userData.putExtra("email", userEmail);
                    userData.putExtra("phone", phone.getText().toString());
                    userData.putExtra("password", password.getText().toString());
                    startActivity(userData);
                }
                // Else, relay all flags and send an error message
                else{
                    for (String flag : flags){
                        Toast.makeText(UserRegistration.this, flag, Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
    // Method to determine if the email address is valid based of a regular expression
    public static boolean isValidEmail(String email) {
        // Define the email regex pattern
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // If the email is null, return false
        if (email == null) {
            return false;
        }

        // Match the email string with the regex pattern
        Matcher matcher = pattern.matcher(email);

        // Return whether the email matches the pattern
        return matcher.matches();
    }
    // Check if the first/last name contains numbers method
    public static boolean containsNumbers(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    // Check if the first/last name contains special characters method
    public static boolean containsSpecialCharacters(String input) {
        String specialCharacters = "!@#$%^&*()_+[]{}|;:',.<>?/~`-=";
        for (char c : input.toCharArray()) {
            if (specialCharacters.contains(Character.toString(c))) {
                return true;
            }
        }
        return false;
    }
}