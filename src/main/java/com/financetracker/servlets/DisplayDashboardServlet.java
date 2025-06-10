package com.financetracker.servlets;

import java.io.IOException;

import com.financetracker.dao.BudgetProcess;
import com.financetracker.dao.CheckUser;
import com.financetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;


@WebServlet("/dashboard")
public class DisplayDashboardServlet extends CheckUser{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = validateUser(request, response);
            if (user == null) return;

            int currentYear = LocalDate.now().getYear();
            BudgetProcess bp = new BudgetProcess();
            boolean status = bp.checkYearlyBudget(user.getUserId(), String.valueOf(currentYear));
            request.setAttribute("status", status);

            request.setAttribute("user", user);

            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
            } else {
                response.getWriter().write("An unexpected error occurred after response was committed.");
            }
        }
    }

}
