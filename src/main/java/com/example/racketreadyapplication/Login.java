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
        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);
        DatabaseHelper db = new DatabaseHelper(this);
        EditText email = findViewById(R.id.edtLoginEmail);
        EditText password = findViewById(R.id.edtLoginPassword);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, UserRegistration.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> flags = new ArrayList<>();
                if (email.getText().toString().isEmpty()){
                    flags.add("Please enter an email!");
                }
                if (password.getText().toString().isEmpty()){
                    flags.add("Please enter a password!");
                }
                if (flags.isEmpty()){
                    boolean c = db.checkLogin(email.getText().toString(), password.getText().toString());
                    if (c){
                        Cursor cur = db.getUser(email.getText().toString());
                        while(cur.moveToNext()){
                            editor.putString("fullName", cur.getString(1));
                            editor.putString("email", cur.getString(2));
                            editor.putString("dob", cur.getString(5));
                            editor.putString("skillLevel", cur.getString(6));
                            editor.putString("preferredDays", cur.getString(7));
                            editor.putString("preferredTime", cur.getString(8));
                            editor.putInt("matchTypeID", cur.getInt(11));
                            editor.apply();
                        }
                        startActivity(new Intent(Login.this, MainActivity.class));
                    }else{
                        Toast.makeText(Login.this, "Invalid Login. Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }else {
                    for (String flag : flags){
                        Toast.makeText(Login.this, flag, Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
}