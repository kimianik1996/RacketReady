package com.example.racketreadyapplication;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class UserRegistration3 extends AppCompatActivity {

    Bitmap imgChosen = null;
    ImageView img = null;
    private static final int REQUEST_PERMISSION = 100;
    private static final int PICK_IMAGE = 101;
    private static final String PREF_USERID =  "userid";

    DatabaseHelper db;
    private static final String dateFormat = "dd/MM/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_registration3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText editTextDOB = findViewById(R.id.edtRegDOB);
        ListView timeList = findViewById(R.id.listTimes);
        Button btnImage = findViewById(R.id.btnImage);
        Button btnCreateUser = findViewById(R.id.btnCreateUser);
        Button btnBack = findViewById(R.id.btnReg3Back);
        Spinner spinLocation = findViewById(R.id.spinLocation);
        img = findViewById(R.id.imgRegProfPic);
        db = new DatabaseHelper(this);

        ArrayList<String> times = new ArrayList<>();
        times.add("Morning");
        times.add("Afternoon");
        times.add("Evening");

        ArrayAdapter adapter;
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, times);
        timeList.setAdapter(adapter);

        StringBuilder selectedTimes= new StringBuilder();
        timeList.setOnItemClickListener(((parent, view, position, id) -> {
            selectedTimes.setLength(0);
            for (int i =0; i < timeList.getCount(); i++){
                if(timeList.isItemChecked(i)){
                    selectedTimes.append(times.get(i)).append(", ");
                }
            }
        }));

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                String name = null;
                String email = null;
                String phone = null;
                String password = null;
                String dob = null;
                String skillLevel = null;
                String preferredDays = null;
                String preferredTime = null;
                byte[] profilePic = null;
                String location = null;
                String matchType = null;

                if (intent != null){
                    name = intent.getStringExtra("name");
                    email = intent.getStringExtra("email");
                    phone = intent.getStringExtra("phone");
                    password = intent.getStringExtra("password");
                    skillLevel = intent.getStringExtra("skillLevel");
                    matchType = intent.getStringExtra("matchType");
                    preferredDays = intent.getStringExtra("preferredDays");
                }
                ArrayList<String> flags = new ArrayList<>();

                int matchtypeID;

                assert matchType != null;
                if (matchType.equals("Singles")){
                    matchtypeID = 0;
                }
                else{
                    matchtypeID = 1;
                }

                String dobTest = editTextDOB.getText().toString();
                if (isValidDate(dobTest, dateFormat)){
                    dob = dobTest;
                }
                else{
                    flags.add("Please add a valid DOB (MM/DD/YYYY)");
                }


                /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                if (imgChosen == null){
                    profilePic = null;
                }else{
                    imgChosen.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    profilePic = outputStream.toByteArray();
                }*/

                preferredTime = selectedTimes.toString();
                if(preferredTime.isEmpty()){
                    flags.add("Please enter at least 1 preferred playtime!");
                }

                if(spinLocation.getSelectedItem().toString().isEmpty()){
                    flags.add("Please enter a location");
                }
                location = spinLocation.getSelectedItem().toString();

                if (flags.isEmpty()){


                    Boolean isInserted = db.addUser(name,email,phone,password,dob,skillLevel,preferredDays,preferredTime,profilePic, location, matchtypeID);
                    if (isInserted){
//                        profilePic = new byte[]{(byte) R.drawable.novak};
//                        // Josh code below
//                        db.addUser("admin", "e@e.ca", "6045557676", "admin", "07/06/1998", "Intermediate", "Monday", "Afternoon", profilePic, "British Columbia", 1);
                        int userId = db.getUserId(email, password);
                        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(PREF_USERID, userId);
                        editor.apply();
//                        //Josh Code above

                        Toast.makeText(UserRegistration3.this, "User Created!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(UserRegistration3.this, Login.class));
                    }
                    else{
                        Toast.makeText(UserRegistration3.this, "Unable To Create User", Toast.LENGTH_LONG).show();
                    }
                }else{
                    for (String flag : flags){
                        Toast.makeText(UserRegistration3.this, flag, Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegistration3.this, UserRegistration2.class));
            }
        });

    }
    // Date Function
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



    // Below are a bunch of functions from chatgpt for getting an image from the android gallery. it don't work, so I just kinda left them here.
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                imgChosen = BitmapFactory.decodeStream(imageStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                // Permission denied
            }
        }
    }


}