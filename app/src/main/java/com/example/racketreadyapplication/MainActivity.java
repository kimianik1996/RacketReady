package com.example.racketreadyapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Users user;
    private static final String PREF_USERID =  "userid";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnFindMatch = findViewById(R.id.findMatchBtn);
        //Josh Code Below
        int userID;
        String email;
        String password;


        DatabaseHelper db = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getInt("userid",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();

        email = sharedPreferences.getString("email","");
        password = sharedPreferences.getString("password", "");

        userID = db.getUserId(email, password);

        editor.putInt(PREF_USERID, userID);

        Log.d("post-login-master", "Your new login id is " + userID);
        Log.d("post-password", "Your password is " + password);
        Log.d("post-email", "Your email is " + email);

        editor.apply();

        //Josh Code Above
        btnFindMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, FindMatchFilters.class));
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}