package com.financetracker.servlets;

import java.io.IOException;

import com.financetracker.dao.CheckUser;
import com.financetracker.dao.TransactionDAO;
import com.financetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delete")
public class DeleteTransactionServlet extends CheckUser {
    private TransactionDAO transactionDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        transactionDAO = new TransactionDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = validateUser(request, response);
            if (user == null) return;
            
            int transactionId = Integer.parseInt(request.getParameter("id"));
            boolean deleted = transactionDAO.deleteTransaction(transactionId, user.getUserId());
            
            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found or not owned by user");
            }
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transaction ID");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}