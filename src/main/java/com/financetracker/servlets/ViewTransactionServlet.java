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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException,IOException{
        String idParameter = request.getParameter("id");
         if (idParameter == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Transaction ID is missing");
            return;
        }
        try{
            int id = Integer.parseInt(idParameter);
            User user = validateUser(request, response);
            if(user== null){
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
                return;
            }

            List<Transaction> transactions = transactionDAO.getTransactionById(id,user.getUserId());
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("/WEB-INF/views/view.jsp").forward(request, response);
            

        }catch(NumberFormatException ex){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transaction ID format"); 
        }catch(Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }
   
}