package com.financetracker.servlets;

import java.io.IOException;

import com.financetracker.dao.BudgetProcess;
import com.financetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/budget")
public class BudgetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/budget.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
       HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    int userId = user.getUserId();
    String period = request.getParameter("period");
    String budgetAmountStr = request.getParameter("budgetAmount");

    if (period == null || budgetAmountStr == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields.");
        return;
    }

    try {
        double budgetAmount = Double.parseDouble(budgetAmountStr);

        BudgetProcess budgetProcess = new BudgetProcess();
        budgetProcess.addBudget(userId, period, budgetAmount);

        response.sendRedirect(request.getContextPath() + "/dashboard");
    } catch (NumberFormatException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid budget amount.");
    }
    }
}
