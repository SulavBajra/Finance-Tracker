package com.financetracker.dao;

import java.sql.Connection;

import com.financetracker.model.DatabaseConnection;

public class ImportProcess {
    public void importCSV(){
        try(Connection connection = DatabaseConnection.getConnection()){
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}   
