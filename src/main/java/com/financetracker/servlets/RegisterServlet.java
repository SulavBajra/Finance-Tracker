package com.financetracker.servlets;

import com.financetracker.dao.PasswordUtils;
import com.financetracker.dao.UserDAO;
import com.financetracker.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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
        request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
                String username = request.getParameter("username").trim();
                String email = request.getParameter("email").trim();
                String password = request.getParameter("password");
                String rePassword = request.getParameter("repassword");
        
            
                String validationError = userDao.validateUser(email, password, rePassword, username);
                if (validationError != null) {
                    request.setAttribute("error", validationError);
                    request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
                    return;
                }
        
                String hashedPassword;
                String salt = PasswordUtils.generateSalt();
                try {
                    hashedPassword = PasswordUtils.hashPassword(password, salt);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    request.setAttribute("error", "An error occurred while processing your request. Please try again.");
                    request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
                    return;
                }
        
            
                User user = new User(username, email, hashedPassword, salt);
        
                if (userDao.registerUser(user)) {
                    response.sendRedirect(request.getContextPath() + "/auth/login.jsp?registration=success");
                } else {
                    request.setAttribute("error", "Registration failed. Username or email already exists.");
                    request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
                }
            }
    }