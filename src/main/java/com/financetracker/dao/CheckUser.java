package com.financetracker.dao;

import java.io.IOException;

import com.financetracker.model.User;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public abstract class CheckUser extends HttpServlet {
    protected User validateUser(HttpServletRequest request,HttpServletResponse response)
    throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return null;
        }
        return (User) session.getAttribute("user");
    }   
}
