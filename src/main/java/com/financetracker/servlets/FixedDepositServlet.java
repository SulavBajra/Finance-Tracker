package com.financetracker.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/fixedDeposit")
public class FixedDepositServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        // Forward request to JSP page
        request.getRequestDispatcher("/WEB-INF/views/fixedDeposit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
                request.getRequestDispatcher("/WEB-INF/views/fixedDeposit.jsp").forward(request, response);
        // Optional: Handle form submission here if you want POST
    }
}
