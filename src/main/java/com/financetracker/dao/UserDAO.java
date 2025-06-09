package com.financetracker.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.financetracker.model.DatabaseConnection;
import com.financetracker.model.User;

public class UserDAO {
    public boolean registerUser(User user){
        String sql = "INSERT INTO users (username, email, password,salt) VALUES (?, ?, ?,?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getSalt());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String email,String password) throws NoSuchAlgorithmException{
        String sql = "SELECT * FROM users WHERE email = ? ";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedHash = resultSet.getString("password");
                String salt = resultSet.getString("salt");
                if (PasswordUtils.verifyPassword(password, storedHash, salt)) {
                    User user = new User();
                    user.setUserId(resultSet.getInt("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(storedHash);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String validateUser(String email,String password,String repassword,String username){
        String error;
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
            error = "All fields are required.";
            return error;
        }
        if (!username.matches("^[a-zA-Z][a-zA-Z0-9_]{2,19}$")) {
            error = "Username must be 3-20 characters long and can only contain letters, numbers, and underscores.";
            return error;
        }
        if(isEmailValid(email)==false){
            error = "Invalid email format.";
            return error;
        }
        if (password.length() < 8 || password.length() > 20) {
            error = "Password must be between 8 and 20 characters.";
            return error;
        }
        if (!password.equals(repassword)) {
            error = "Passwords do not match.";
            return error;
        }
        if (isEmailTaken(email)) {
            error = "Email is already taken.";
            return error;
        }
        return null;
    }


    public boolean isEmailTaken(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   private boolean isEmailValid(String email) {
        String emailRegex = "(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        return email.matches(emailRegex);
    }


}
