// package com.financetracker.servlets;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;

// import com.financetracker.dao.CheckUser;
// import com.financetracker.dao.TransactionDAO;
// import com.financetracker.model.Transaction;
// import com.financetracker.model.User;
// import com.opencsv.CSVParser;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.Part;


// @WebServlet("/import")
// public class ImportServlet extends CheckUser{
//     private TransactionDAO transactionDAO;
//     @Override
//     public void init()throws ServletException{
//         super.init();
//         transactionDAO = new TransactionDAO();
//     }

//     @Override
//     protected void doGet(HttpServletRequest request,HttpServletResponse response)
//     throws ServletException,IOException{
//         try {
//             User user = validateUser(request, response);
//             if (user == null) return;
//             request.setAttribute("user", user);
//             request.getRequestDispatcher("/WEB-INF/views/import.jsp").forward(request, response);
            
//         } catch (Exception e) {
//             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//         }
//     }

//     @Override
//     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//         Part filePart = request.getPart("file");
//         if (filePart == null || filePart.getSize() == 0) {
//             request.setAttribute("error", "No file uploaded");
//             request.getRequestDispatcher("/import.jsp").forward(request, response);
//             return;
//         }

//         try (InputStream inputStream = filePart.getInputStream();
//              BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//              Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/finance_tracker", "admin", "admin123")) {

//             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
//             String userId = (String) request.getSession().getAttribute("userId"); // Ensure session has user ID
            
//             if (userId == null) {
//                 response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
//                 return;
//             }

//             String query = "INSERT INTO transactions (user_id, transaction_date, type, category, amount, description) VALUES (?, ?, ?, ?, ?, ?)";
//             try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//                 for (CSVRecord record : csvParser) {
//                     pstmt.setString(1, userId);
//                     pstmt.setDate(2, java.sql.Date.valueOf(record.get("transaction_date")));
//                     pstmt.setString(3, record.get("type"));
//                     pstmt.setString(4, record.get("category"));
//                     pstmt.setDouble(5, Double.parseDouble(record.get("amount")));
//                     pstmt.setString(6, record.get("description"));
//                     pstmt.addBatch();
//                 }
//                 pstmt.executeBatch();
//             }

//             request.setAttribute("message", "Transactions imported successfully");
//             response.sendRedirect(request.getContextPath() + "/list");
//         } catch (Exception e) {
//             request.setAttribute("error", "Error processing file: " + e.getMessage());
//             request.getRequestDispatcher("/import.jsp").forward(request, response);
//         }
//     }
// }
