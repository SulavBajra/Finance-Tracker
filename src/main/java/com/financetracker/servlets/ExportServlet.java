package com.financetracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.financetracker.dao.TransactionDAO;
import com.financetracker.model.Transaction;
import com.financetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/export")
public class ExportServlet extends HttpServlet {
    private TransactionDAO transactionDAO;

    @Override
    public void init()throws ServletException{
        super.init();
        transactionDAO = new TransactionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        writer.println("Transaction ID,Amount,Type,Category,Description,Transaction Date");
        try{
            User user = validateUser(request, response);
            if (user == null) return;
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + user.getUsername() + ".csv\"");
            List<Transaction> transactions = transactionDAO.getUserTransaction(user.getUserId());
            if (transactions.isEmpty()) {
                response.getWriter().write("No transactions found for the user.");
                return;
            }
            for(Transaction transaction : transactions){
                writer.println(transaction.getTransactionId() + "," +
                        transaction.getAmount() + "," +
                        transaction.getType() + "," +
                        transaction.getCategory() + "," +
                        transaction.getDescription() + "," +
                        transaction.getTransactionDate());
            }

        }catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }finally{
            writer.flush();
            writer.close();
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
