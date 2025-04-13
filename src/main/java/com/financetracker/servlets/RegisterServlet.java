package com.financetracker.servlets;

import com.financetracker.dao.UserDAO;
import com.financetracker.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        userDao = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Show registration form
        request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        User user = new User(username, email, password);
        
        if (userDao.registerUser(user)) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp?registration=success");
        } else {
            request.setAttribute("error", "Registration failed. Username or email already exists.");
            // request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
            response.sendRedirect(request.getContextPath() + "/auth/register.jsp?error=Registration failed. Username or email already exists.");
        }
    }
}