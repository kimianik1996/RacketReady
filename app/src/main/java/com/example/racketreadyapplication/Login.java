package com.example.racketreadyapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize UI components and Shared Preferences
        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);
        DatabaseHelper db = new DatabaseHelper(this);

// TODO: Ensure You Uncomment on every line of code that contain dob, from Josh


       // db.deleteUsers();
       //db.prepopulateTables();
       //db.deleteAllCourts();
        EditText email = findViewById(R.id.edtLoginEmail);
        EditText password = findViewById(R.id.edtLoginPassword);
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // If pressed sign up, go to the UserRegistration activity
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, UserRegistration.class));
            }
        });


        // Josh code below
        //  c)  db.addUser("Johnny Huckleberry IV", "johnny@email.com", "6045557676", "johnny", "07/06/1998", "Intermediate", "Monday, Wednesday, Friday,", "Afternoon", profilePic, "British Columbia", 1);
//       db.addUser("admin", "e@e.ca", "6045557676", "admin", "07/06/1998", "Intermediate", "Monday", "Afternoon", null, "British Columbia", 1);
//      //Josh Code above

// db.deleteUsers();
//   db.prepopulateUsers(); // This prepopulates a random sample of users, if turning this off/deleting it, please

/**
 *
 *
 *
 */
        // Login will do checks to validate the credentials of the user
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Flag array to store any error details to be then outputted
                ArrayList<String> flags = new ArrayList<>();
                //Check if email or password are empty
                if (email.getText().toString().isEmpty()){
                    flags.add("Please enter an email!");
                }
                if (password.getText().toString().isEmpty()){
                    flags.add("Please enter a password!");
                }
                // If the flag array returns empty, there are no empty boxes. Continue with validation.
                if (flags.isEmpty()){
                    // Reset SharedPreferences
                    editor.clear();
                    editor.apply();
                    // Check the database for a record that contains the correct email and password combination
                    boolean c = db.checkLogin(email.getText().toString(), password.getText().toString());
                    // If found, store the variable into shared preferences and then take them to the main activity (this will be the navigation hub, probably)
                    if (c){
                        Cursor cur = db.getUserByEmail(email.getText().toString());
                        while(cur.moveToNext()){
                            editor.putInt("userid", cur.getInt(0));
                            editor.apply();
                        }
                        startActivity(new Intent(Login.this, MainActivity.class));
                    }else{// Else, reply that credentials were invalid.
                        Toast.makeText(Login.this, "Invalid Login. Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }else { // If flag contains errors, loop messages for each error.
                    for (String flag : flags){
                        Toast.makeText(Login.this, flag, Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
}