package com.example.racketready;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RacketReadyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RacketReadyApplication.class, args);




	}
/** 		CREATE TABLE INFORMATION
 * CREATE TABLE users (
 *     user_id INT AUTO_INCREMENT PRIMARY KEY,
 *     full_name VARCHAR(100) NOT NULL,
 *     email VARCHAR(100) NOT NULL UNIQUE,
 *     password_hash VARCHAR(255) NOT NULL,
 *     date_of_birth DATE NOT NULL,
 *     skill_level ENUM('Beginner', 'Intermediate', 'Advanced', 'Professional') NOT NULL,
 *     preferred_match SET('Singles', 'Doubles') NOT NULL,
 *     location VARCHAR(100) NOT NULL,
 *     profile_picture_url VARCHAR(255),
 *     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
