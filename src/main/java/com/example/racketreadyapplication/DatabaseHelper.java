package com.example.racketreadyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Blob;


public class DatabaseHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "RacketReady.db";

    final static int DB_VERSION = 8;

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

    final static String Table2Name = "SkillLevels";
    final static String Table2Col1 = "skillName";
    final static String Table2Col2 = "description";
    final static String Table2Col3 = "eloScoreThresh";

    final static String Table3Name = "MatchType";
    final static String Table3Col0 = "typeID";
    final static String Table3Col1 = "styleName";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table1Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table2Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table3Name);
        onCreate(db);
    }

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

    public Cursor getUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT * FROM " + Table1Name);
        Cursor c = db.rawQuery(query,null);
        return c;
    }

    public boolean checkEmails(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT email FROM " + Table1Name);
        Cursor c = db.rawQuery(query, null);
        Boolean check = false;
        while(c.moveToNext()){
            if (email.equals(c.getString(0))){
                return true;
            }
        }
        return check;
    }
    public Cursor getUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT * FROM " + Table1Name + " WHERE email= '" + email + "'");
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public boolean checkLogin(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT email, password FROM " + Table1Name);
        Cursor c = db.rawQuery(query,null);
        Boolean check = false;
        while(c.moveToNext()){
            if (email.equals(c.getString(0)) && password.equals(c.getString(1))){
                return true;
            }
        }
        return check;
    }

}
