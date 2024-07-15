package com.example.racketreadyapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

        int userID;
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getInt("userid",0);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.clear();

        Log.d("pre-login-master", "Your new login id is " + userID);
        byte[] profilePic = new byte[]{(byte) R.drawable.novak};
        // Josh code below
            //  c)  db.addUser("Johnny Huckleberry IV", "johnny@email.com", "6045557676", "johnny", "07/06/1998", "Intermediate", "Monday, Wednesday, Friday,", "Afternoon", profilePic, "British Columbia", 1);
//   b)     db.addUser("admin", "e@e.ca", "6045557676", "admin", "07/06/1998", "Intermediate", "Monday", "Afternoon", profilePic, "British Columbia", 1);
//        //Josh Code above

// db.deleteUsers();
//    a)    db.prepopulateUsers(); // This prepopulates a random sample of users, if turning this off/deleting it, please

/**
 * HOW TO Prepopulate Users/ Admin Account
 * Step 1: Uncomment a), run app, and then stop running app, and comment
 * Step 2:Uncomment b), run app, and then stop running app. From restarting the app you can login from this account, comment b) afterwards
 * If needed, unlock c) for an additional user, make sure you comment afterwards to avoid duplications
 */


        EditText email = findViewById(R.id.edtLoginEmail);
        EditText password = findViewById(R.id.edtLoginPassword);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        // If pressed sign up, go to the UserRegistration activity
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, UserRegistration.class));
            }
        });
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
                    // Check the database for a record that contains the correct email and password combination
                    boolean c = db.checkLogin(email.getText().toString(), password.getText().toString());
                    // If found, store the variable into shared preferences and then take them to the main activity (this will be the navigation hub, probably)
                    if (c){
                        Cursor cur = db.getUser(email.getText().toString());
                        while(cur.moveToNext()){
                            editor.putString("fullName", cur.getString(1));
                            //Josh code below
                            ed.putString("email", cur.getString(2));
                            ed.putString("password", cur.getString(4));
                            //Josh code above
                            editor.putString("email", cur.getString(2));
                            editor.putString("dob", cur.getString(5));
                            editor.putString("skillLevel", cur.getString(6));
                            editor.putString("preferredDays", cur.getString(7));
                            editor.putString("Location", cur.getString(10));
                            editor.putString("preferredTime", cur.getString(8));
                            editor.putInt("matchTypeID", cur.getInt(11));
                            editor.apply();
                            //Josh code below
                            ed.apply();
                            //Josh code above
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