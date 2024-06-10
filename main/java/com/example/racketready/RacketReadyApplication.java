package com.example.racketready;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RacketReadyApplication {
	public static void main(String[] args) {
		SpringApplication.run(RacketReadyApplication.class, args);

	}


/** 		CREATE TABLE INFORMATION
 * CREATE TABLE users (
 *     user_id INT AUTO_INCREMENT PRIMARY KEY, 0 <<< Index
 *     full_name VARCHAR(100) NOT NULL, 1
 *     email VARCHAR(100) NOT NULL UNIQUE, 2
 *     username VARCHAR(20) NOT NULL UNIQUE, 3
 *     password_hash VARCHAR(255) NOT NULL, 4
 *     date_of_birth DATE NOT NULL, 5
 *     skill_level ENUM('Beginner', 'Intermediate', 'Advanced', 'Professional') NOT NULL, 6
 *     preferred_match SET('Singles', 'Doubles') NOT NULL, 7
 *     preferred_days VARCHAR(255) NOT NULL, 8
 *     preferred_time VARCHAR(255) NOT NULL, 9
 *     location VARCHAR(100) NOT NULL, 10
 *     profile_picture_url VARCHAR(255), 11
 *     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 12
 *     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP 13
 * );
 * Additional Information
 * user_id: An auto-incrementing primary key to uniquely identify each user.
 * full_name: The user's full name, which can be up to 100 characters long.
 * email: The user's email address, which must be unique and up to 100 characters long.
 * password_hash: The hashed password for the user's account to ensure security.
 * skill_level: The user's skill level, which can be one of the specified ENUM values.
 * preferred_match: The user's preferred match type, which can include both singles and doubles.
 * location: The user's location, which can be up to 100 characters long.
 * profile_picture_url: The URL to the user's profile picture, which can be up to 255 characters long.
 * created_at: The timestamp for when the user account was created.
 * updated_at: The timestamp for when the user account was last updated, automatically updated on any change.
 * To Hold the information from the above fields, a database table called users will be created and each input corresponding to a field in the database table . I could go on to design a normalised database tables in the first normal forms but all the data and the ways this web application will work is not yet fully grasped. This table will be normalised as we progress if need be be but for now lets go with a single table. I also considered having a username but I think only an email address should be used to login for simplicity.
 */
}
