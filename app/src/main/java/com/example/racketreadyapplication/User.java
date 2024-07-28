package com.example.racketreadyapplication;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class User {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String dob;
    private String skillLevel;
    private String preferredDays;
    private String preferredTime;
    private byte[] profilePic;
    private String location;
    private int matchTypeID;
    private static int age;

    private String userid;


    public User(){

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public User(String name, String email, String phone, String password, String dob, String skillLevel, String preferredDays, String preferredTime, byte[] profilePic, String location, int matchTypeID) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.password = password;
        this.skillLevel = skillLevel;
        this.preferredDays = preferredDays;
        this.preferredTime = preferredTime;
        this.profilePic = profilePic;
        this.location = location;
        this.matchTypeID = matchTypeID;
       // getAge(dob);
    }



    public User(String userId, String fullName, String email, String phone, String password, String dob, String skillLevel, String preferredDays, String preferredTime, byte[] profilePic, String location, String matchTypeId) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getPreferredDays() {
        return preferredDays;
    }

    public void setPreferredDays(String preferredDays) {
        this.preferredDays = preferredDays;
    }

    public String getPreferredTime() {
        return preferredTime;
    }

    public void setPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMatchTypeID() {
        return matchTypeID;
    }

    public void setMatchTypeID(int matchTypeID) {
        this.matchTypeID = matchTypeID;
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void getAge(String dob){
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDate date = LocalDate.parse(dob, format);
//        LocalDate now;
//        now = LocalDate.parse(LocalDate.now().format(format));
//        calculateAge(date, now);
//    }

    @SuppressLint("NewApi")
    public static void calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
                age =  Period.between(birthDate, currentDate).getYears();

        } else {
                age = 0;
        }
    }

    public int getUserAge(){
        return age;
    }

}
