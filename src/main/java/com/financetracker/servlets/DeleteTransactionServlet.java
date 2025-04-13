package com.financetracker.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.financetracker.dao.TransactionDAO;
import com.financetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/delete")
public class DeleteTransactionServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteTransactionServlet.class.getName());
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
                response.sendRedirect(request.getContextPath() + "/transactions/list.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found or not owned by user");
            }
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transaction ID");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting transaction", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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