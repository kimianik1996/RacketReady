package com.example.racketreadyapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserProfile extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DatabaseHelper db = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userid", 0);
        Cursor userData = db.getUser(userID);
        User user = new User();
        while (userData.moveToNext()) {
            user.setName(userData.getString(1));
            user.setEmail(userData.getString(2));
            user.setPhone(userData.getString(3));
            user.setPassword(userData.getString(4));
            //     user.setDob(userData.getString(5));
            user.setSkillLevel(userData.getString(6));
            user.setPreferredDays(userData.getString(7));
            user.setPreferredTime(userData.getString(8));
            user.setProfilePic(userData.getBlob(9));
            user.setLocation(userData.getString(10));
            user.setMatchTypeID(userData.getInt(11));
        }
        TextView tvAge = findViewById(R.id.tvAge);
        TextView tvUserName = findViewById(R.id.txtUserName);
        TextView tvSkillLevel = findViewById(R.id.tvUserSkillLevel);
        TextView tvBirthday = findViewById(R.id.tvUserBirthday);
        TextView tvUserDays = findViewById(R.id.tvUserDays);
        TextView tvLocation = findViewById(R.id.tvUserLocation);
        TextView tvTimes = findViewById(R.id.tvUserTime);
        TextView tvEmail = findViewById(R.id.tvUserEmail);
        TextView tvPhone = findViewById(R.id.tvUserPhone);
        TextView tvMembership = findViewById(R.id.tvUserMembershipStatus);
        Button btnEdit = findViewById(R.id.btnSettings);
        Button btnMembership = findViewById(R.id.btnMembership);
        Button btnBack = findViewById(R.id.btnUserBack);
        tvAge.setText("Age : " + user.getUserAge());
        Bitmap bitmap = BitmapFactory.decodeByteArray(user.getProfilePic(), 0, user.getProfilePic().length);
        ImageView profPic = findViewById(R.id.imgUserProfPic);
        profPic.setImageBitmap(bitmap);
        tvUserName.setText("Hello, " + user.getName());
        tvSkillLevel.setText(user.getSkillLevel());
        tvBirthday.setText(user.getDob());
        tvUserDays.setText(user.getPreferredDays());
        tvLocation.setText("Looking for games in " + user.getLocation());
        String[] times = user.getPreferredTime().split(", ");
        StringBuilder timeList = new StringBuilder();
        for (int i = 0; i < times.length; i++) {
            if (i == times.length - 1) {
                timeList.append(times[i]);
            } else {
                timeList.append(times[i]);
                timeList.append(" and ");
            }
        }
        tvTimes.setText("Preferred Times: " + timeList);
        tvEmail.setText("Email: " + user.getEmail());
        tvPhone.setText("Phone: " + user.getPhone());
        Cursor c = db.checkMembership(userID);
        StringBuilder s = new StringBuilder();
        while (c.moveToNext()) {
            s.append(c.getString(0));
        }
        tvMembership.setText(s);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, Settings.class));
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, MainActivity.class));
            }
        });


    }
}