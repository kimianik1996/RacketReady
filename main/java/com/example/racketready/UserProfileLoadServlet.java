package com.example.racketready;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/profileloadservlet")
public class UserProfileLoadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection conn = DatabaseHelper.getDatabaseConnection();
            Statement stmt = conn.createStatement();
            String sql = "select * from users where username = '"+request.getParameter("username")+"'";
            ResultSet rs = stmt.executeQuery(sql);

            User user = new User();

            while(rs.next()) {
                user.setId(rs.getInt("user_id"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setLocation(rs.getString("location"));
                user.setProfilepic(rs.getURL("profilepic"));
                user.setSkill_level(rs.getString("skill_level"));
                user.setPreffered_match(rs.getString("preferred_match"));
                user.setUsername(rs.getString("username"));
            }
            rs.close();
            stmt.close();
            request.setAttribute("profilepic", user.getProfilepic());
            request.setAttribute("user", user.getUsername());
            request.setAttribute("email", user.getEmail());
            request.setAttribute("location", user.getLocation());
            request.setAttribute("skill_level", user.getSkill_level());
            String[] matches = user.getSkill_level().split(",");
            request.setAttribute("preffered_match", matches);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
