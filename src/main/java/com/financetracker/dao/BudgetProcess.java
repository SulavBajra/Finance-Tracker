package com.financetracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.financetracker.model.DatabaseConnection;

public class BudgetProcess {
   public void addBudget(int userId, String period, double budgetAmount) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        String query = "INSERT INTO budget (user_id, period, budget_amount) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, period);
            stmt.setDouble(3, budgetAmount);
            stmt.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error adding budget to the database.", e);
    }
}
}
