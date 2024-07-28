package com.example.racketreadyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MatchUserDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_user_details);

        int userID;
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getInt("userid",0);
        DatabaseHelper db = new DatabaseHelper(this);
        //Josh Code Above


        Button btn = findViewById(R.id.btnSendMatch);

        String str_full_name = getIntent().getStringExtra("user-fullname");
        String str_skillLevel = getIntent().getStringExtra("user-skillLevel");
        String str_location = getIntent().getStringExtra("user-location");

        TextView fullname = findViewById(R.id.users_name);
        TextView skilllevel = findViewById(R.id.users_skillLevel);
        TextView location = findViewById(R.id.users_location);

        fullname.setText(str_full_name);
        skilllevel.setText(str_skillLevel);
        location.setText(str_location);


        String email = getIntent().getStringExtra("user-email");
        String password = getIntent().getStringExtra("user-password");

        int userid = db.getUserId(email, password);

        // get opponents userId
        Log.d("user-id", "your opponents id is " + userid);



btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MatchUserDetails.this, MatchDetailsRequest.class);
        intent.putExtra("selectedUser", userid);
        startActivity(intent);
    }
});

    }

}