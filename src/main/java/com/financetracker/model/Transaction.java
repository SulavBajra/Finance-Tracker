package com.financetracker.model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int userId;
    private double amount;
    private String type;
    private String category;
    private String description;
    private String transactionDate;
    private Timestamp createdAt;

    public Transaction() {
        this.transactionId = 0;
        this.userId = 0;
        this.amount = 0.0;
        this.type = "";
        this.category = "";
        this.description = "";
        this.transactionDate = "";
    }

    public Transaction(int userId, double amount, String type, String category, String description, String transactionDate) {
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
        this.transactionDate = transactionDate;
    }
    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
