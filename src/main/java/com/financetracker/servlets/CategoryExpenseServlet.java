package com.financetracker.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.financetracker.model.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/getExpenseData")
public class CategoryExpenseServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
    
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT category, SUM(amount) AS total FROM transactions WHERE user_id = ? AND type = 'expense' GROUP BY category";
                PreparedStatement stmt = conn.prepareStatement(sql);
                int userId = ((com.financetracker.model.User) request.getSession().getAttribute("user")).getUserId();
                stmt.setInt(1, userId);
    
                ResultSet rs = stmt.executeQuery();
    
                List<Map<String, Object>> data = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> entry = new HashMap<>();
                    entry.put("category", rs.getString("category"));
                    entry.put("total", rs.getDouble("total"));
                    data.add(entry);
                }
    
                PrintWriter out = response.getWriter();
                Gson gson = new Gson();
                out.print(gson.toJson(data));
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
    
    
