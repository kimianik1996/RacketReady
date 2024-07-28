package com.example.racketreadyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.List;

public class searchMatches extends AppCompatActivity {


    RecyclerView recyclerView;
    Adapter adapter;

    int userID;
    int matchtype;

    String preferredDay;
    RecyclerView.LayoutManager layoutManager;

    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_matches);
        //Josh Code Below
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getInt("userid", 0);
        //Josh Code Above

        db = new DatabaseHelper(searchMatches.this);


//        if (db == null) {
//            Log.e("searchMatches", "DatabaseHelper is null");
//        } else {
//            Log.d("searchMatches", "DatabaseHelper initialized");
//        }

        List<User> users = getUsers();
//        if (users == null) {
//            Log.e("searchMatches", "Users list is null");
//        } else {
//            Log.d("searchMatches", "Users list initialized with size: " + users.size());
//        }


        // Commented Code is to error check and then log if there's any issues.


        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(users, searchMatches.this);
        recyclerView.setAdapter(adapter);


    }

    private List<User> getUsers() {


        List<User> users = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("filterMatches", MODE_PRIVATE);

        // int matchtype = sharedPreferences.getInt("match_type", 0);
        String skillLevel = sharedPreferences.getString("skilllevel", "");
        String preferredDay = sharedPreferences.getString("preferredDay", "");
        String location = sharedPreferences.getString("location", "");
        int match_type = sharedPreferences.getInt("match_type", 0);


//
//        Cursor cursor = db.viewAllUsers(userID);
        //    Cursor cursor = db.getUserId(141);
        Cursor c = db.getUserId(userID);

//
//        Log.d("location", "location is" + location);
//        Log.d("skillLevel", "skill level is" + skillLevel);
//        Log.d("preferredDays", "preferred Days are " + preferredDay);
//        Log.d("matchTypeId", "matchType Id is " + match_type);

        c.close();

        Cursor cursor = db.filterMatchedUsers(userID, skillLevel, preferredDay, location, match_type);
        try {
            while (cursor.moveToNext()) {
                String userId = cursor.getString(cursor.getColumnIndexOrThrow("userID"));
                String fullName = cursor.getString(cursor.getColumnIndexOrThrow("fullname"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String dob = cursor.getString(cursor.getColumnIndexOrThrow("dob"));
                skillLevel = cursor.getString(cursor.getColumnIndexOrThrow("skillLevel"));
                String preferredDays = cursor.getString(cursor.getColumnIndexOrThrow("preferredDays"));
                String preferredTime = cursor.getString(cursor.getColumnIndexOrThrow("preferredTime"));
                byte[] profilePic = cursor.getBlob(cursor.getColumnIndexOrThrow("profilePic"));
                location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                int matchTypeId = cursor.getInt(cursor.getColumnIndexOrThrow("matchTypeID"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    users.add(new User(fullName, email, phone, password, dob, skillLevel, preferredDays, preferredTime,
                            profilePic, location, matchTypeId));

                }

            }
        } finally {
            cursor.close();
        }
        Log.d("user size", "the amount of users are " + users.size());
        return users;
    }
}

