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

@WebServlet("/view")
public class ViewTransactionServlet extends CheckUser{
    private TransactionDAO transactionDAO;

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
            List<Transaction> transaction = transactionDAO.getTransactionById(transactionId, user.getUserId());
            if (transaction == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found or not owned by user");
                return;
            }
            request.setAttribute("transaction", transaction);
            request.getRequestDispatcher("/WEB-INF/views/view.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transaction ID");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
