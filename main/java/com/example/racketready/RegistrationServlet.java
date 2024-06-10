package com.example.racketready;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

@WebServlet("/registerationServlet")
public class RegistrationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errs = new ArrayList<>();
        // Profile Picture, First and Last name (combined into full name)
        URL url = new URL(request.getParameter("profilePic")); // Get Profile Pic URL
        String firstName = request.getParameter("firstName"); // Get First Name From Forme
        String lastName = request.getParameter("lastName"); // Get Last Name From Forme
        String fullName = null;
        if (firstName != null && lastName != null) {
            fullName = firstName + " " + lastName;
        }else{
            if (firstName == null){
                errs.add("Please enter a First Name!");
            } else {
                errs.add("Please enter a Last Name!");
            }
        }
        // Email
        String email = request.getParameter("email"); // Get Email From Forme
        if (email == null) {
            errs.add("Please enter an Email!");
        }
        // Username
        String username = request.getParameter("username");
        if (username == null) {
            errs.add("Please enter a Username!");
        }

        //Password
        String password = request.getParameter("password"); // Get First Password From Form
        String confirmPassword = request.getParameter("confirmPassword"); // Get Confirmation Password From Form E

        if (password == null || confirmPassword == null) {
            errs.add("Please enter a Password!");
        }
        if (!password.equals(confirmPassword)) {
            errs.add("Passwords do not match!");
        }
        // Date Of Birth
        String dobString = request.getParameter("dob");
        Date dob = Date.valueOf(dobString);

        // OLD Date Conversion thing, this is replaced with the above code
        //StringWriter sw = new StringWriter();
        //sw.append(dob_year).append("-").append(dob_month).append("-").append(dob_day).append("\n");
        //String dob_string = sw.toString();
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        //LocalDate dob = LocalDate.parse(dob_string, formatter);

        String[] match_type = request.getParameterValues("match_type"); // Match Type array, form data returns collection of checkboxes
        StringWriter matches = new StringWriter();
        if (match_type != null) {
            for (String match : match_type) {
                matches.append(match);
            }
        }
        String preferredMatch = matches.toString();
        if (preferredMatch == null) {
            errs.add("Please enter a Preferred Match!");
        }
        // Preferred Days of the Week
        String[] matchWeekdays = request.getParameterValues("matchWeekdays"); // Match Type array, form data returns collection of checkboxes
        String[] matchWeekends = request.getParameterValues("matchWeekends");
        StringWriter matchDaysStr = new StringWriter();
        if (matchWeekdays != null) {
            for (String day : matchWeekdays) {
                matchDaysStr.append(day);
            }
        }
        if (matchWeekends != null) {
            for (String day : matchWeekends){
                matchDaysStr.append(day);
            }
        }
        String preferredDays = matchDaysStr.toString();
        if (preferredDays == null) {
            errs.add("Please enter your preferred match days!");
        }

        // Preferred Match Time
        String[] matchTimes = request.getParameterValues("matchTimes");
        StringWriter matchTimesStr = new StringWriter();
        if (matchWeekdays != null) {
            for (String time : matchTimes) {
                matchTimesStr.append(time);
            }
        }
        String preferredTimes = matchTimesStr.toString();

        // Skill Level
        String skillLevel = request.getParameter("skillLevel"); // Skill Level array, returns only 1 radio value
        if (skillLevel == null) {
            errs.add("Please enter a Skill Level!");
        }
        // Location Data
        String location = request.getParameter("location"); // Location data
        if (location == null) {
            errs.add("Please enter your Location!");
        }
        // Terms of Service Check
        String terms = request.getParameter("terms"); // Terms Of Service Check
        if (terms == null) {
            errs.add("Please accept the Terms Of Use!");
        }
        // If errors are empty, append data to database.
        if (errs.isEmpty()){
            try{
                Connection con = DatabaseHelper.getDatabaseConnection();
                PreparedStatement insertData = con.prepareStatement("INSERT INTO users(full_name,email,username,password_hash,date_of_birth,skill_level,preferred_match,preferred_days,preferred_times,location,profile_picture_url) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
                insertData.setString(1, fullName);
                insertData.setString(2, email);
                insertData.setString(3,username);
                insertData.setInt(4, password.hashCode());
                insertData.setDate(5, dob);
                insertData.setString(6, skillLevel);
                insertData.setString(7, preferredMatch);
                insertData.setString(8, preferredDays);
                insertData.setString(9, preferredTimes);
                insertData.setString(10, location);
                insertData.setURL(11,url);
                insertData.executeUpdate();
                con.close();
                response.sendRedirect("login.html");
                // Success
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        // Else relay back errors
    }
}