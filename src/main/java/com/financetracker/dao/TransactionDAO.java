package com.financetracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.financetracker.model.DatabaseConnection;
import com.financetracker.model.Transaction;

public class TransactionDAO {
    public boolean addTransaction(Transaction transaction){
        String sql = "INSERT INTO transactions (user_id, amount, type, category, description, transaction_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, transaction.getUserId());
            preparedStatement.setDouble(2, transaction.getAmount());
            preparedStatement.setString(3, transaction.getType());
            preparedStatement.setString(4, transaction.getCategory());
            preparedStatement.setString(5, transaction.getDescription());
            preparedStatement.setString(6, transaction.getTransactionDate());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> getUserTransaction(int userID){
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getInt("transaction_id"));
                transaction.setUserId(resultSet.getInt("user_id"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setType(resultSet.getString("type"));
                transaction.setCategory(resultSet.getString("category"));
                transaction.setDescription(resultSet.getString("description"));
                transaction.setTransactionDate(resultSet.getString("transaction_date"));
                transaction.setCreatedAt(resultSet.getTimestamp("created_at"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public boolean deleteTransaction(int transactionId,int userId){
        String sql = "DELETE FROM transactions WHERE transaction_id = ? AND user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, transactionId);
            preparedStatement.setInt(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransaction(Transaction transaction,int transactionId){
        String sql = "UPDATE transactions SET amount = ?, type = ?, category = ?, description = ?, transaction_date = ? WHERE transaction_id = ? and user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(7, transaction.getUserId());
            preparedStatement.setInt(6, transactionId);
            preparedStatement.setDouble(1, transaction.getAmount());
            preparedStatement.setString(2, transaction.getType());
            preparedStatement.setString(3, transaction.getCategory());
            preparedStatement.setString(4, transaction.getDescription());
            preparedStatement.setString(5, transaction.getTransactionDate());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> getTransactionById(int transactionId,int userId){ 
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE transaction_id = ? and user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, transactionId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getInt("transaction_id"));
                transaction.setUserId(resultSet.getInt("user_id"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setType(resultSet.getString("type"));
                transaction.setCategory(resultSet.getString("category"));
                transaction.setDescription(resultSet.getString("description"));
                transaction.setTransactionDate(resultSet.getString("transaction_date"));
                transactions.add(transaction);
                return transactions;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Double> getUserSummary(int userId) throws SQLException {
        Map<String, Double> summary = new HashMap<>();
        
        String sql = "SELECT type, SUM(amount) as total FROM transactions " +
                     "WHERE user_id = ? GROUP BY type";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                summary.put(rs.getString("type"), rs.getDouble("total"));
            }
        }
        
        // Ensure all expected keys exist
            Double income = getTotalByType(userId, "income");
        summary.put("totalIncome", income != null ? income : 0.0);
        
        // Get expense total
        Double expense = getTotalByType(userId, "expense");
        summary.put("totalExpense", expense != null ? expense : 0.0);
        
        // Calculate balance
        summary.put("balance", summary.get("totalIncome") - summary.get("totalExpense"));
        
        return summary;
        
        
    }

    private Double getTotalByType(int userId, String type) throws SQLException {
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getDouble(1) : null;
        }
    }
    
    public List<Transaction> getRecentTransactions(int userId, int limit) 
    throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        
        String sql = "SELECT * FROM transactions WHERE user_id = ? " +
                     "ORDER BY transaction_date DESC, transaction_id DESC LIMIT ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                transactions.add(mapTransaction(rs));
            }
        }
        
        return transactions;
    }
    
    public List<Transaction> getTransactionsByCategory(int userId) throws SQLException{
        List<Transaction> categoryDataList = new ArrayList<>();
        String sql = "SELECT category, SUM(amount) AS total FROM transactions WHERE user_id = ? GROUP BY category";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setCategory(rs.getString("category"));
                transaction.setAmount(rs.getDouble("total"));
                categoryDataList.add(transaction);
                return categoryDataList;
            }
        } catch (SQLException e) {  
            e.printStackTrace();
        }
        return categoryDataList;
    }

    private Transaction mapTransaction(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setTransactionId(rs.getInt("transaction_id"));
        t.setUserId(rs.getInt("user_id"));
        t.setAmount(rs.getDouble("amount"));
        t.setType(rs.getString("type"));
        t.setCategory(rs.getString("category"));
        t.setDescription(rs.getString("description"));
        t.setTransactionDate(rs.getString("transaction_date"));
        return t;
    }
}




