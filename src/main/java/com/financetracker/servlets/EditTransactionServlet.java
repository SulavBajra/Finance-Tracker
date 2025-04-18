package com.financetracker.servlets;

import java.io.IOException;

import com.financetracker.dao.CheckUser;
import com.financetracker.dao.TransactionDAO;
import com.financetracker.model.Transaction;
import com.financetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/edit")
public class EditTransactionServlet extends CheckUser{
    private static TransactionDAO transactionDAO;
    @Override
    public void init() throws ServletException {
        super.init();
        transactionDAO = new TransactionDAO();        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        try {
            User user = validateUser(request, response);
            if (user == null) return;
            
            int transactionId = Integer.parseInt(request.getParameter("id"));
            Transaction transaction = transactionDAO.getTransactionById(transactionId, user.getUserId());
            if (transaction == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found or not owned by user");
                return;
            }
            request.setAttribute("transaction", transaction);
            request.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transaction ID");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
         try {
            User user = validateUser(request, response);
            if (user == null) return;
            
            String type = request.getParameter("type");
            String category = request.getParameter("category");
            String description = request.getParameter("description");
            double amount = Double.parseDouble(request.getParameter("amount"));
            String transactionDate = request.getParameter("transactionDate");
            int transactionId = Integer.parseInt(request.getParameter("id"));
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }
            Transaction transaction = new Transaction(
                user.getUserId(),
                amount,
                type,
                category,
                description,
                transactionDate
            );
            if (transactionDAO.updateTransaction(transaction,transactionId)) {
                // response.sendRedirect(request.getContextPath() + "/list");
                request.setAttribute("message", "Transaction updated successfully");
                request.setAttribute("transaction", transaction);
                request.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found or not owned by user");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transaction ID or amount");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
