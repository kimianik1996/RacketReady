package com.example.racketreadyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "RacketReadyMaster.db";
    // RacketReady database edition: 10
    // RacketReadyMaster database edition: 1
    final static int DB_VERSION = 2;
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
    // Table4: Membership Columns and names
    final static String Table4Name = "Membership";
    final static String Table4Col1 = "membership_name";
    final static String Table4Col2 = "membership_price";
    final static String Table4Col3 = "enableCompetitive";
    final static String Table4Col4 = "enableCourtPayments";
    final static String Table4Col5 = "enableTournamentCreation";
    final static String Table4Col6 = "membership_duration";
    // Table5: MembershipStatus Columns and names
    final static String Table5Name = "MembershipStatus";
    final static String Table5Col1 = "userID";
    final static String Table5Col2 = "membership_name";
    final static String Table5Col3 = "expiration_date";
    final static String Table5Col4 = "amount_paid";
    // Table6: Match Columns and names
    final static String Table6Name = "MatchTable";
    final static String Table6Col1 = "matchID";
    final static String Table6Col2 = "typeID";
    final static String Table6Col3 = "courtID";
    final static String Table6Col4 = "score"; // Can be stored as side1/side2. (Ex. 11/4, side1 wins). Or whatever way anyone wants to store it
    final static String Table6Col5 = "isTournament";
    final static String Table6Col6 = "isCompetitive";
    // Table7: Court Columns and names
    final static String Table7Name = "Court";
    final static String Table7Col1 = "courtID";
    final static String Table7Col2 = "location_name";
    final static String Table7Col3 = "games_played";
    final static String Table7Col4 = "payment";
    final static String Table7Col5 = "available_spots";

    // Table8: MatchHistory Columns and names
    final static String Table8Name = "MatchHistory";
    final static String Table8Col1 = "userID";
    final static String Table8Col2 = "matchID";
    final static String Table8Col3 = "userStanding";
    final static String Table8Col4 = "match_date";

    // Table9: Notifications Columns and names
    final static String Table9Name = "Notifications";
    final static String Table9Col1 = "userID";
    final static String Table9Col2 = "matchID";
    final static String Table9Col3 = "notif_standing";
    final static String Table9Col4 = "notif_text";
    // Table10: Ranks Columns and names
    final static String Table10Name = "Ranks";
    final static String Table10Col1 = "rankName";
    final static String Table10Col2 = "elo_score_thresh"; // Ranks are given depending on if the users own elo score is within the threshold of a rank (ex. If user elo is 2.4, if Gold rank is put at 2.0 they would be in Gold. If the user's score goes beyond 2.0, they move up a rank.)
    final static String Table10Col3 = "description";
    // Table11: Rankings Columns and names
    final static String Table11Name = "Rankings";
    final static String Table11Col1 = "userID";
    final static String Table11Col2 = "elo_score";
    final static String Table11Col3 = "rankName";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    // On Create method, creates all tables and populates skill levels and match type with pre-made data.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + Table1Name + "(" + Table1Col0 +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + Table1Col1 + " TEXT, " + Table1Col2 + " TEXT, " + Table1Col3 + " TEXT, " +
                Table1Col4 + " TEXT, " + Table1Col5 + " TEXT, " + Table1Col6 + " TEXT, " + Table1Col7 + " TEXT, " +
                Table1Col8 + " TEXT, "  + Table1Col9 + " BLOB, " + Table1Col10 + " TEXT, " + Table1Col11 + " INTEGER)";
        db.execSQL(query);
        query = "CREATE TABLE " + Table2Name + "(" + Table2Col1 + " TEXT PRIMARY KEY, " + Table2Col2 + " TEXT, " + Table2Col3 + " REAL)";
        db.execSQL(query);
        query = "CREATE TABLE " + Table3Name + "(" + Table3Col0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Table3Col1 + " TEXT)";
        db.execSQL(query);
        db.execSQL("INSERT INTO SkillLevels VALUES ('Beginner', 'A player who has just started playing tennis.', 1.00)," +
                " ('Intermediate', 'A player who has experienced tennis casually.', 2.00), " +
                "('Advanced', 'A player who has skills far above a normal person in tennis.', 3.00)," +
                "('Professional', 'A player who encompasses what it means to be a tennis player', 4.00)");
        query = "CREATE TABLE " + Table4Name + "(" + Table4Col1 + " TEXT PRIMARY KEY, " + Table4Col2 + " LONG, " + Table4Col3 + " INTEGER, " + Table4Col4
        + " INTEGER, " + Table4Col5 + " INTEGER, " + Table4Col6 + " INTEGER)";
        db.execSQL(query);
        db.execSQL("INSERT INTO Membership VALUES ('Standard', 0.00, 0,0,0,0)," +
                "('RacketPlus Monthly', 10.00, 1,1,0, 31)," +
                "('RacketPlus Yearly', 120.00, 1,1,0, 365)," +
                "('RacketReallyReady', 145.00, 1,1,1,365)");
        query = "CREATE TABLE " + Table5Name + "(" + Table5Col1 + " INTEGER, " + Table5Col2 + " TEXT, " + Table5Col3 + " TEXT, "
                + Table5Col4 + " LONG, PRIMARY KEY (" + Table5Col1 +", " + Table5Col2+ "))";
        db.execSQL(query);
        db.execSQL("INSERT INTO MatchType(styleName) VALUES ('Singles'), ('Doubles')");
        query = "CREATE TABLE " + Table6Name + "(" + Table6Col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Table6Col2 + " INTEGER, " + Table6Col3 + " INTEGER, " + Table6Col4 + " TEXT, " + Table6Col5 + " INTEGER, " + Table6Col6 + " INTEGER)";
        db.execSQL(query);
        query = "CREATE TABLE " + Table7Name + "(" + Table7Col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Table7Col2 + " TEXT, " + Table7Col3 + " INTEGER, " + Table7Col4 + " LONG, " +  Table7Col5 + " INTEGER)";
        db.execSQL(query);
        db.execSQL("INSERT INTO Court(location_name, games_played, payment, available_spots) VALUES ('Kennedy Tennis Courts', 10, 0, 10), ('Sunshine Hills Tennis Club', 16, 0, 15), ('RacketReady Tennis Court', 16, 5.00, 16)");
        query = "CREATE TABLE " + Table8Name + "(" + Table8Col1 + " INTEGER, " + Table8Col2 + " TEXT, " + Table8Col3 + " TEXT, " + Table8Col4 + " TEXT, PRIMARY KEY (" + Table8Col1 + ", " + Table8Col2 + "))";
        db.execSQL(query);
        query = "CREATE TABLE " + Table9Name + "(" + Table9Col1 + " INTEGER, " + Table9Col2 + " INTEGER, " + Table9Col3 + " TEXT, " + Table9Col4 + " TEXT, PRIMARY KEY (" + Table9Col1 + ", " + Table9Col2 + "))";
        db.execSQL(query);
        query = "CREATE TABLE " + Table10Name + "(" + Table10Col1 + " TEXT PRIMARY KEY, " + Table10Col2 + " LONG, " + Table10Col3 + " TEXT)";
        db.execSQL(query);
        db.execSQL("INSERT INTO Ranks VALUES ('Bronze',1.00, 'Beginner Level Rank')," +
                "('Silver',2.00, 'Intermediate Level Rank')," +
                "('Gold', 3.00,'Advanced Level Rank')," +
                "('Platnium',4.00,'Professional Level Rank')," +
                "('Emerald',5.00, 'Beyond Professional Level Rank')");
        query = "CREATE TABLE " + Table11Name + "(" + Table11Col1 + " INTEGER, " + Table11Col2 + " LONG, " + Table11Col3 + " TEXT)";
        db.execSQL(query);
    }
    // On Upgrade method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table1Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table2Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table3Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table4Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table5Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table6Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table7Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table8Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table9Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table10Name);
        db.execSQL("DROP TABLE IF EXISTS " + Table11Name);
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

    public boolean updateUser(int userID, String fullName, String email, String phone, String password, String dob, String preferredDays, String preferredTime, byte[] profilePic, String location, int matchType){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE User SET fullname= '" + fullName + "', email= '" + email + "', phone= '" + phone + "', password= '" + password + "', dob= '" + dob +"', preferredDays= '" + preferredDays + "', preferredTime='"
                + preferredTime + "', profilePic= x" + profilePic + ", location= '" + location + "', matchType=" + matchType + "WHERE userID="+userID;
        db.execSQL(query);
        return true;
    }

    public boolean updateUserCreds(int userID, String fullName, String phone, String dob){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE User SET fullname= '" + fullName + "', phone= '" + phone + "', dob= '" + dob +"' WHERE userID="+userID;
        db.execSQL(query);
        return true;
    }

    public boolean updatePassword(int userID, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE User SET password= '" + password + "' WHERE userID=" +userID;
        db.execSQL(query);
        return true;
    }

    public boolean updateMatchPreferences(int userID, String preferredDays, String preferredTime, String location, int matchType){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE User SET preferredDays= '" + preferredDays + "', preferredTime='" + preferredTime + "', location= '" + location + "', matchTypeID=" + matchType + " WHERE userID="+userID;
        db.execSQL(query);
        return true;
    }


    public Cursor checkMembership(int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT membership_name FROM MembershipStatus WHERE userID=" + userID);
        Cursor c = db.rawQuery(query, null);
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
    public Cursor getUserByEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT * FROM " + Table1Name + " WHERE email= '" + email + "'");
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public Cursor getUser(int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT * FROM " + Table1Name + " WHERE userID= " + userID);
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
    public boolean addMembership(int userID, String membershipName, String expirationDate, double amountPaid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(("SELECT userID FROM " + Table1Name + " WHERE userID=" + userID),null);
        ContentValues contentValues = new ContentValues();
        while(c.moveToNext()){
            contentValues.put(Table5Col1,c.getInt(0));
        }
        contentValues.put(Table5Col2, membershipName);
        contentValues.put(Table5Col3, expirationDate);
        contentValues.put(Table5Col4, amountPaid);
        long r = db.insert(Table5Name, null, contentValues);
        return r > 0;
    }

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
                {"Admin", "e@e.com", "(123) 456-7890", "admin", "01/01/1990",
                        "Beginner", "Monday", "Morning", "British Columbia", "1"},
                {"Jane Smith", "jane.smith@example.com", "(234) 567-8901", "password456", "02/02/1991",
                        "Beginner", "Monday", "Morning", "British Columbia", "1"},
                {"Michael Johnson", "michael.johnson@example.com", "(345) 678-9012", "password789", "03/03/1992",
                        "Beginner", "Monday", "Morning", "British Columbia", "1"},
                {"Emily Brown", "emily.brown@example.com", "(456) 789-0123", "passwordabc", "04/04/1993",
                        "Beginner", "Monday", "Morning", "British Columbia", "2"},
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

//    public void prepopulateTables() {
//        prepopulateUsers();
//
//        String[][] courtsData = {
//                {"Kennedy Tennis Courts", "10", "0", "10"},
//                {"Sunshine Hills Tennis Club", "16", "0", "16"},
//                {"RacketReady Tennis Court", "16", "5.00", "16"}
//        };
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        for (String[] courtData : courtsData) {
//            db.execSQL("INSERT INTO Court(location_name, games_played, payment, available_spots) VALUES (?, ?, ?, ?)",
//                    new Object[]{courtData[0], Integer.parseInt(courtData[1]), Double.parseDouble(courtData[2]), Integer.parseInt(courtData[3])});
//        }
//    }

    public void prepopulateTables(){
        prepopulateUsers();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Court(location_name, games_played, payment, available_spots) VALUES ('Kennedy Tennis Courts', 10, 0, 10), ('Sunshine Hills Tennis Club', 16, 0, 16), ('RacketReady Tennis Court', 16, 5.00, 16)");
    }
    public void deleteAllCourts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table7Name,null,null);
        db.close();

    }


    public Cursor filterMatchedUsers(int userId, String skillLevel,  String preferredDay, String location, int matchType) {
        List<User> users = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Table1Name + " WHERE " +
                Table1Col6 + " = ? AND " +
                Table1Col7 + " LIKE ? AND " +
                Table1Col10 + " = ? AND " +
                Table1Col11 + " = ? AND " +
                "userID != ?";

        return db.rawQuery(query, new String[]{skillLevel, "%" + preferredDay + "%", location, String.valueOf(matchType), String.valueOf(userId)});


    }

    public boolean addMatchHistory(int userID, int matchID, String userStanding, String matchDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table8Col1, userID);
        values.put(Table8Col2, matchID);
        values.put(Table8Col3, userStanding);
        values.put(Table8Col4, matchDate);
        long result = db.insert(Table8Name, null, values);
        return result > 0;
    }

    public boolean addMatch(int typeID, int courtID, String score, int isTournament, int isCompetitive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table6Col2, typeID);
        values.put(Table6Col3, courtID);
        values.put(Table6Col4, score);
        values.put(Table6Col5, isTournament); // Assuming 1 for true, 0 for false
        values.put(Table6Col6, isCompetitive); // Assuming 1 for true, 0 for false
        long result = db.insert(Table6Name, null, values);
        return result > 0;
    }


    public Cursor getUserId(int userID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT * FROM " + Table1Name + " WHERE userID = ?");
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(userID)});
        return c;
    }

}
