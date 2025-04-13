package com.financetracker.servlets;

import java.io.IOException;

import com.financetracker.dao.TransactionDAO;
import com.financetracker.model.Transaction;
import com.financetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/add")
public class AddTransactionServlet extends HttpServlet {
    // private static final Logger logger = Logger.getLogger(AddTransactionServlet.class.getName());
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
            // response.sendRedirect(request.getContextPath() + "/transactions/add.jsp");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/transactions/add.jsp").forward(request, response);
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = validateUser(request, response);
            if (user == null) return;
            
            double amount = Double.parseDouble(request.getParameter("amount"));
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }
            String type = request.getParameter("type");
            String category = request.getParameter("category");
            String description = request.getParameter("description");
            String transactionDate = ""+request.getParameter("transactionDate");
            Transaction transaction = new Transaction(
                user.getUserId(),
                amount,
                type,
                category,
                description,
                transactionDate
            );
            if (transactionDAO.addTransaction(transaction)) {
                // List<Transaction> transactions = transactionDAO.getUserTransaction(user.getUserId());
                // request.setAttribute("transactions", transactions);
                 request.setAttribute("transaction", transaction);
                // response.sendRedirect(request.getContextPath() + "/transactions/list.jsp");
                request.getRequestDispatcher("transactions/list.jsp").forward(request, response);
            } else {
                request.setAttribute("error",transactionDate+"Failed to add transaction");
                request.getRequestDispatcher("/transactions/add.jsp").forward(request, response);
            }
            
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        } catch (Exception e) {
            // logger.log(Level.SEVERE, "Error adding transaction", e);
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
    
    // private Transaction createTransactionFromRequest(HttpServletRequest request, User user) {
    //     double amount;
    //     try {
    //         amount = Double.parseDouble(request.getParameter("amount"));
    //     } catch (NumberFormatException e) {
    //         throw new IllegalArgumentException("Invalid amount format");
    //     }
        
    //     String type = request.getParameter("type");
    //     String category = request.getParameter("category");
    //     String description = request.getParameter("description");
    //     String transactionDate = request.getParameter("transaction_date");
        
    //     if (type == null || !(type.equals("income") || type.equals("expense"))) {
    //         throw new IllegalArgumentException("Invalid transaction type");
    //     }
        
    //     if (category == null || category.trim().isEmpty()) {
    //         throw new IllegalArgumentException("Category is required");
    //     }
        
    //     return new Transaction(
    //         user.getUserId(),
    //         amount,
    //         type,
    //         category,
    //         description,
    //         transactionDate
    //     );
    // }
}