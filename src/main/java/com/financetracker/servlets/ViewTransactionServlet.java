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

@WebServlet("/view")
public class ViewTransactionServlet extends CheckUser {
    private TransactionDAO transactionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        transactionDAO = new TransactionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String idParameter = request.getParameter("transaction_id");
        int transactionId;

        try {
            transactionId = Integer.parseInt(idParameter);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transaction ID");
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        try {
            Transaction transaction = transactionDAO.getTransactionById(transactionId, user.getUserId());
            if (transaction != null) {
                request.setAttribute("user", user);
                request.setAttribute("transaction", transaction);
            } else {
                request.setAttribute("error", "Transaction not found.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Optional: log for debugging
            request.setAttribute("error", "Error fetching transaction: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/view.jsp").forward(request, response);
    }
}
