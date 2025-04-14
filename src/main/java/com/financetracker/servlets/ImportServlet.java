package com.financetracker.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.financetracker.dao.TransactionDAO;
import com.financetracker.model.Transaction;
import com.financetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/import")
public class ImportServlet extends HttpServlet{
    private TransactionDAO transactionDAO;
    @Override
    public void init()throws ServletException{
        super.init();
        transactionDAO = new TransactionDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        try{
            User user = validateUser(request, response);
            if (user == null){
                response.sendRedirect("transactions/login.jsp");
            }
            Part filePart = request.getPart("file");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()));
            String line;
            while((line = reader.readLine())!= null){
                String[] data = line.split(",");
                if(data.length != 6){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid CSV format");
                    return;
                }
                String transaction_date = data[0];
                String type = data[1];
                String category = data[2];
                double amount = Double.parseDouble(data[3]);
                String description = "Added from CSV";
                Transaction transaction = new Transaction(user.getUserId(),amount,type,category,description,transaction_date);
                if(transactionDAO.addTransaction(transaction)){
                    response.sendRedirect(request.getContextPath() + "transactions/list.jsp");
                }else{
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to import transaction");
                    response.sendRedirect(request.getContextPath() + "transactions/list.jsp");
                }   
            }
        }catch(Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the file.");
        }
    }

     private User validateUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return null;
        }
        return (User) session.getAttribute("user");
    }
}
