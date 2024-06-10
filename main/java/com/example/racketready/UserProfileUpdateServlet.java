package com.example.racketready;

import javassist.compiler.Parser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@WebServlet("/editprofileservlet")
public class UserProfileUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errs = new ArrayList<String>();

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

        String email = request.getParameter("email"); // Get Email From Forme
        if (email == null) {
            errs.add("Please enter an Email!");
        }

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

        String skillLevel = request.getParameter("skillLevel"); // Skill Level array, returns only 1 radio value
        if (skillLevel == null) {
            errs.add("Please enter a Skill Level!");
        }

        String location = request.getParameter("location"); // Location data
        if (location == null) {
            errs.add("Please enter a valid location!");
        }

        try {
            Connection conn = DatabaseHelper.getDatabaseConnection();
            Statement stmt = conn.createStatement();
            User user = new User();
            user.setFull_name(fullName);
            user.setId(Integer.parseInt(request.getParameter("id")));
            user.setEmail(email);
            user.setPreffered_match(preferredMatch);
            user.setSkill_level(skillLevel);
            user.setLocation(location);
            String sql = "UPDATE users SET full_name='" + user.getFull_name() + "', email='" + user.getEmail() + "',preferred_match='"+ user.getPreffered_match() + "', skill_level='" +
                    user.getSkill_level() + "', location='" + user.getLocation() +"'WHERE id=" + user.getId();
            stmt.executeUpdate(sql);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
