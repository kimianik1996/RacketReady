package com.example.racketreadyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;


public class DatabaseHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "RacketReady.db";
    // Current database edition: 8
    final static int DB_VERSION = 8;
    // Table1: User Columns and names
    final static String Table1Name = "User";
    final static String Table1Col0 = "userID";
    final static String Table1Col1 = "fullname";
    final static String Table1Col2 = "email";
    final static String Table1Col3 = "phone";
    final static String Table1Col4 = "password";
    final static String Table1Col5 = "dob";
    final static String Table1Col6 = "skillLevel";
    final static String Table1Col7 = "preferredDays";
    final static String Table1Col8 = "preferredTime";
    final static String Table1Col9 = "profilePic";
    final static String Table1Col10 = "location";
    final static String Table1Col11 = "matchTypeID";
    // Table2: SkillLevels Columns and names
    final static String Table2Name = "SkillLevels";
    final static String Table2Col1 = "skillName";
    final static String Table2Col2 = "description";
    final static String Table2Col3 = "eloScoreThresh";
    // Table3: MatchType Columns and names
    final static String Table3Name = "MatchType";
    final static String Table3Col0 = "typeID";
    final static String Table3Col1 = "styleName";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    // On Create method, creates all tables and populates skill levels and match type with pre-made data.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + Table1Name + "(" + Table1Col0 +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + Table1Col1 + " TEXT, " + Table1Col2 + " TEXT, " + Table1Col3 + " TEXT, " +
                Table1Col4 + " TEXT, " + Table1Col5 + " TEXT, " + Table1Col6 + " TEXT, " + Table1Col7 + " TEXT, " +
                Table1Col8 + " TEXT, "  + Table1Col9 + " BLOB, " + Table1Col10 + " TEXT, " + Table1Col11 + " INTEGER)";
        db.execSQL(query1);
        String query2 = "CREATE TABLE " + Table2Name + "(" + Table2Col1 + " TEXT PRIMARY KEY, " + Table2Col2 + " TEXT, " + Table2Col3 + " REAL)";
        db.execSQL(query2);
        String query3 = "CREATE TABLE " + Table3Name + "(" + Table3Col0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Table3Col1 + " TEXT)";
        db.execSQL(query3);
        db.execSQL("INSERT INTO SkillLevels VALUES ('Beginner', 'A player who has just started playing tennis.', 1.00)," +
                " ('Intermediate', 'A player who has experienced tennis casually.', 2.00), " +
                "('Advanced', 'A player who has skills far above a normal person in tennis.', 3.00)," +
                "('Professional', 'A player who encompasses what it means to be a tennis player', 4.00)");
        db.execSQL("INSERT INTO MatchType(styleName) VALUES ('Singles'), ('Doubles')");
    }
    // On Upgrade method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table1Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table2Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table3Name);
        onCreate(db);
    }
    // Add a user, used in Registration
    public boolean addUser(String fullName, String email, String phone, String password, String dob, String skillLevel, String preferredDays, String preferredTime, byte[] profilePic, String location,int matchType){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table1Col1, fullName);
        values.put(Table1Col2, email);
        values.put(Table1Col3, phone);
        values.put(Table1Col4, password);
        values.put(Table1Col5, dob);
        values.put(Table1Col6, skillLevel);
        values.put(Table1Col7, preferredDays);
        values.put(Table1Col8, preferredTime);
        values.put(Table1Col9, profilePic);
        values.put(Table1Col10, location);
        values.put(Table1Col11, matchType);
        long r = db.insert(Table1Name, null, values);
        if (r>0){
            return true;
        }else return false;
    }
    // Get All Users from the Database (Not yet implemented into anything yet)
    public Cursor getUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT * FROM " + Table1Name);
        Cursor c = db.rawQuery(query,null);
        return c;
    }
    // Email check method
    public boolean checkEmails(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT email FROM " + Table1Name);
        Cursor c = db.rawQuery(query, null);
        // From the cursor, check if the email is equal to any records in the database. If true, means that email is already in use
        Boolean check = false;
        while(c.moveToNext()){
            if (email.equals(c.getString(0))){
                return true;
            }
        }
        return check;
    }
    // Get user, used by Login to get data on a successful login


    public Cursor getUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT * FROM " + Table1Name + " WHERE email= '" + email + "'");
        Cursor c = db.rawQuery(query, null);
        return c;
    }



    // Method to check for a login.
    public boolean checkLogin(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT email, password FROM " + Table1Name);
        Cursor c = db.rawQuery(query,null);
        // Check if a record in the database contains both a matching email and password. If correct, login is successful.
        Boolean check = false;
        while(c.moveToNext()){
            if (email.equals(c.getString(0)) && password.equals(c.getString(1))){
                return true;
            }
        }
        return check;
    }

    // Josh Code Below
public int getUserId(String email, String password) {
    SQLiteDatabase db = this.getReadableDatabase();
    int userId = 0;
    Cursor c = db.query(
            Table1Name,
            new String[]{Table1Col0},
            "email=? AND password=?",
            new String[]{email, password},
            null,
            null,
            null
    );

    if (c != null) {
        if (c.moveToFirst()) {
            userId = c.getInt(c.getColumnIndexOrThrow(Table1Col0));
        }
        c.close();
    }
    return userId;

}
//    try {
//        if (c.moveToFirst()) {
//            int idColumn = c.getColumnIndexOrThrow(Table1Col0);
//            if (idColumn != -1) {
//                userId = c.getInt(idColumn);
//            } else {
//
//            }
//        }
//    } finally{
//            c.close();
//            db.close();
//        }

//
//    return userId;
//}

public Cursor viewAllUsers(int userid) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + Table1Name +
                " WHERE userID <> ?";
        return sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(userid)});
}

public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table1Name, null, null);
        db.close();
}

public void prepopulateUsers() {
        byte[] profilePic = new byte[]{(byte) R.drawable.novak};

    String[][] usersData = {
            {"John Doe", "john.doe@example.com", "(123) 456-7890", "password123", "01/01/1990",
                    "Beginner", "Monday", "Morning", "Ontario", "1"},
            {"Jane Smith", "jane.smith@example.com", "(234) 567-8901", "password456", "02/02/1991",
                    "Intermediate", "Tuesday", "Afternoon", "Quebec", "1"},
            {"Michael Johnson", "michael.johnson@example.com", "(345) 678-9012", "password789", "03/03/1992",
                    "Beginner", "Wednesday", "Morning", "British Columbia", "2"},
            {"Emily Brown", "emily.brown@example.com", "(456) 789-0123", "passwordabc", "04/04/1993",
                    "Intermediate", "Thursday", "Afternoon", "Alberta", "2"},
            {"William Wilson", "william.wilson@example.com", "(567) 890-1234", "passwordxyz", "05/05/1994",
                    "Beginner", "Friday", "Morning", "Manitoba", "2"},
            {"Sophia Taylor", "sophia.taylor@example.com", "(678) 901-2345", "password123", "06/06/1995",
                    "Intermediate", "Saturday", "Afternoon", "Saskatchewan", "2"},
            {"Oliver Martinez", "oliver.martinez@example.com", "(789) 012-3456", "password456", "07/07/1996",
                    "Beginner", "Sunday", "Morning", "Nova Scotia", "2"},
            {"Isabella Garcia", "isabella.garcia@example.com", "(890) 123-4567", "password789", "08/08/1997",
                    "Intermediate", "Monday", "Afternoon", "New Brunswick", "1"},
            {"Liam Lopez", "liam.lopez@example.com", "(901) 234-5678", "passwordabc", "09/09/1998",
                    "Beginner", "Tuesday", "Morning", "Prince Edward Island", "1"},
            {"Emma Hernandez", "emma.hernandez@example.com", "(012) 345-6789", "passwordxyz", "10/10/1999",
                    "Intermediate", "Wednesday", "Afternoon", "Newfoundland and Labrador", "2"},
            {"James Adams", "james.adams@example.com", "(123) 456-7890", "password123", "11/11/2000",
                    "Beginner", "Thursday", "Morning", "Ontario", "1"},
            {"Ava Campbell", "ava.campbell@example.com", "(234) 567-8901", "password456", "12/12/2001",
                    "Intermediate", "Friday", "Afternoon", "Quebec", "1"},
            {"Noah Phillips", "noah.phillips@example.com", "(345) 678-9012", "password789", "01/01/2002",
                    "Beginner", "Saturday", "Morning", "British Columbia", "2"},
            {"Mia Evans", "mia.evans@example.com", "(456) 789-0123", "passwordabc", "02/02/2003",
                    "Intermediate", "Sunday", "Afternoon", "Alberta", "1"},
            {"Alexander Turner", "alexander.turner@example.com", "(567) 890-1234", "passwordxyz", "03/03/2004",
                    "Beginner", "Monday", "Morning", "Manitoba", "2"}
    };

    for (String[] userData : usersData) {
        boolean inserted = addUser(userData[0], userData[1], userData[2], userData[3], userData[4],
                userData[5], userData[6], userData[7], profilePic, userData[8], Integer.parseInt(userData[9]));

        if (inserted) {
            Log.d("Prepopulate", "User inserted successfully: " + userData[0]);
        } else {
            Log.e("Prepopulate", "Failed to insert user: " + userData[0]);
        }
    }
}


public Cursor filterMatchedUsers(int userId, String skillLevel,  String preferredDay, String location, String matchType) {
List<Users> users = new ArrayList<>();

SQLiteDatabase db = this.getReadableDatabase();

String query = "SELECT * FROM " + Table1Name + " WHERE " +
        Table1Col6 + " = ? AND " +
        Table1Col7 + " LIKE ? AND " +
        Table1Col10 + " = ? AND " +
        Table1Col11 + " = ? AND " +
        "userID != ?";

    return db.rawQuery(query, new String[]{skillLevel, "%" + preferredDay + "%", location, matchType, String.valueOf(userId)});


}



    public Cursor getUserId(int userID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT * FROM " + Table1Name + " WHERE userID = ?");
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(userID)});
        return c;
    }
}




// Josh Code Above


