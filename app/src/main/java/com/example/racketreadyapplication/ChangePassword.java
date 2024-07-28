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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DatabaseHelper db = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Button btnApply = findViewById(R.id.btnPasswordApply);
        Button btnBack = findViewById(R.id.btnPassBack);
        EditText edtNewPass = findViewById(R.id.edtNewPassword);
        EditText edtCurrentPass = findViewById(R.id.edtCurrentPassword);
        EditText edtConfirmPass = findViewById(R.id.edtConfirmNewPassword);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> flags = new ArrayList<>();
                Cursor c = db.getUser(sharedPreferences.getInt("userID",0));
                if (edtCurrentPass.getText().toString().isEmpty() || edtNewPass.toString().isEmpty() || edtConfirmPass.toString().isEmpty()){
                    flags.add("Missing Field. Please fill out all fields to continue");
                }
                if (!edtNewPass.getText().toString().equals(edtConfirmPass.getText().toString())){
                    flags.add("New Passwords Do Not Match!");
                }
                while (c.moveToNext()){
                    if (!edtCurrentPass.getText().toString().equals(c.getString(4))){
                        flags.add("Password Entered is not your Current Password");
                    }
                }
                if(flags.isEmpty()){
                    db.updatePassword(sharedPreferences.getInt("userID",0),edtNewPass.getText().toString());
                    Toast.makeText(ChangePassword.this, "Password Changed Successfully! Please Login Again",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ChangePassword.this, Login.class));
                }
                else{
                    for (String flag : flags){
                        Toast.makeText(ChangePassword.this,flag, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePassword.this, Settings.class));
            }
        });

    }
}