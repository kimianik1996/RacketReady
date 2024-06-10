package com.example.racketready;

import java.net.URL;
import java.sql.Date;

public class User {
    private int id;
    private String full_name;
    private String email;
    private Date date_of_birth;
    private String skill_level;
    private String preffered_match;
    private String location;
    private URL profilepic;
    private String username;

    public User(){

    }

    public User(int id, String full_name, String email, Date date_of_birth, String skill_level, String preffered_match, String location, URL url, String username) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.skill_level = skill_level;
        this.preffered_match = preffered_match;
        this.location = location;
        this.profilepic = url;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getSkill_level() {
        return skill_level;
    }

    public void setSkill_level(String skill_level) {
        this.skill_level = skill_level;
    }

    public String getPreffered_match() {
        return preffered_match;
    }

    public void setPreffered_match(String preffered_match) {
        this.preffered_match = preffered_match;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public URL getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(URL profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
