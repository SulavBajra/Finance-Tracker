package com.financetracker.servlets;

import java.io.IOException;
import java.util.List;

import com.financetracker.dao.CheckUser;
import com.financetracker.dao.TransactionDAO;
import com.financetracker.model.Transaction;
import com.financetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/list")
public class ListTransactionsServlet extends CheckUser {
    private TransactionDAO transactionDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        transactionDAO = new TransactionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = validateUser(request, response);
            if (user == null) return;
            
            List<Transaction> transactions = transactionDAO.getUserTransaction(user.getUserId());
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(request, response);
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle POST requests if needed
        try {
            User user = validateUser(request, response);
            if (user == null) return;
            
            List<Transaction> transactions = transactionDAO.getUserTransaction(user.getUserId());
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(request, response);
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
