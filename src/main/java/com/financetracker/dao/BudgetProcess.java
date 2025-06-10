package com.financetracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.financetracker.model.DatabaseConnection;

import jakarta.servlet.ServletException;

public class BudgetProcess extends CheckUser{
    private TransactionDAO transactionDAO;

    public BudgetProcess() {
        transactionDAO = new TransactionDAO();
    }
        
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

    public boolean checkYearlyBudget(int userId,String date){
        int budget=0;
        try (Connection conn = DatabaseConnection.getConnection()){
            String query = "SELECT budget_amount FROM budget WHERE user_id = ? AND period='yearly'";
            try(PreparedStatement stmt = conn.prepareStatement(query)){
                stmt.setInt(1, userId);
                ResultSet resultSet = stmt.executeQuery();
                if(resultSet.next()){
                    budget = resultSet.getInt("budget_amount");
                    int yearlyExpense = transactionDAO.getExpenseForYear(userId, date);
                    return yearlyExpense > budget;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
