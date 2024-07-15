package com.example.racketreadyapplication;

public class Users {

    private String userID;
    private String fullname;
    private String email;
    private String phone;
    private String password;
    private String dob;
    private String skillLevel;

    private String preferredDays;

    private String preferredTime;

    private byte[] profilePic;

    private String location;

    private String matchTypeID;

    public Users() {
    }

    public Users(String userID, String fullname, String email, String phone, String password, String dob, String skillLevel, String preferredDays, String preferredTime, byte[] profilePic, String location, String matchTypeID) {
        this.userID = userID;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.dob = dob;
        this.skillLevel = skillLevel;
        this.preferredDays = preferredDays;
        this.preferredTime = preferredTime;
        this.profilePic = profilePic;
        this.location = location;
        this.matchTypeID = matchTypeID;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getMatchTypeID() {
        return matchTypeID;
    }

    public void setMatchTypeID(String matchTypeID) {
        this.matchTypeID = matchTypeID;
    }
}
