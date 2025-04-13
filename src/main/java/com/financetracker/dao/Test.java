package com.financetracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
import com.financetracker.model.*;

public class Test {
    public static void main(String[] args) {
        // TransactionDAO transactionDAO = new TransactionDAO();
        

      String sql = "SELECT * FROM transactions WHERE transaction_id = ? and user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 9);
            preparedStatement.setInt(2, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getInt("transaction_id"));
                System.out.println(resultSet.getInt("user_id"));
                System.out.println(resultSet.getDouble("amount"));
                System.out.println(resultSet.getString("type"));
                System.out.println(resultSet.getString("category"));
                System.out.println(resultSet.getString("description"));
                System.out.println(resultSet.getString("transaction_date"));
                System.out.println("hello");
                // transaction.setUserId(resultSet.getInt("user_id"));
                // transaction.setAmount(resultSet.getDouble("amount"));
                // transaction.setType(resultSet.getString("type"));
                // transaction.setCategory(resultSet.getString("category"));
                // transaction.setDescription(resultSet.getString("description"));
                // transaction.setTransactionDate(resultSet.getString("transaction_date"));
            }

        
        }catch (Exception e) {
            e.printStackTrace();}
    }
}
