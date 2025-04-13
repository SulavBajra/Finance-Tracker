// package com.financetracker.servlets;

// import java.io.IOException;
// import java.util.List;
// import java.util.logging.Level;
// import java.util.logging.Logger;

// import com.financetracker.dao.TransactionDAO;
// import com.financetracker.model.Transaction;
// import com.financetracker.model.User;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.HttpSession;

// @WebServlet("/transactions/*")
// public class TransactionServlet extends HttpServlet {
//     private static final Logger logger = Logger.getLogger(TransactionServlet.class.getName());
//     private TransactionDAO transactionDAO;
    
//     @Override
//     public void init() throws ServletException {
//         super.init();
//         transactionDAO = new TransactionDAO();
//     }

//     @Override
//     protected void doGet(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         try {
//             User user = validateUser(request, response);
//             if (user == null) return;
            
//             String action = request.getPathInfo();
            
//             if (action == null || action.equals("/")) {
//                 handleListTransactions(request, response, user);
//             } else if (action.equals("/add")) {
//                 handleAddForm(request, response);
//             } else if (action.equals("/delete")) {
//                 handleDeleteTransaction(request, response, user);
//             } else {
//                 response.sendError(HttpServletResponse.SC_NOT_FOUND);
//             }
//         } catch (Exception e) {
//             logger.log(Level.SEVERE, "Error in TransactionServlet doGet", e);
//             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//         }
//     }
    
//     @Override
//     protected void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         try {
//             User user = validateUser(request, response);
//             if (user == null) return;
            
//             String action = request.getPathInfo();
            
//             if (action != null && action.equals("/add")) {
//                 handleAddTransaction(request, response, user);
//             } else {
//                 response.sendError(HttpServletResponse.SC_NOT_FOUND);
//             }
//         } catch (Exception e) {
//             logger.log(Level.SEVERE, "Error in TransactionServlet doPost", e);
//             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//         }
//     }
    
//     private User validateUser(HttpServletRequest request, HttpServletResponse response) 
//             throws IOException {
//         HttpSession session = request.getSession(false);
//         if (session == null || session.getAttribute("user") == null) {
//             response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
//             return null;
//         }
//         return (User) session.getAttribute("user");
//     }
    
//     private void handleListTransactions(HttpServletRequest request, HttpServletResponse response, User user)
//             throws ServletException, IOException {
//         List<Transaction> transactions = transactionDAO.getUserTransaction(user.getUserId());
//         request.setAttribute("transactions", transactions);
//         request.getRequestDispatcher("/transactions/list.jsp").forward(request, response);
//     }
    
//     private void handleAddForm(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         request.getRequestDispatcher("/transactions/add.jsp").forward(request, response);
//     }
    
//     private void handleDeleteTransaction(HttpServletRequest request, HttpServletResponse response, User user)
//             throws IOException {
//         try {
//             int transactionId = Integer.parseInt(request.getParameter("id"));
//             boolean deleted = transactionDAO.deleteTransaction(transactionId, user.getUserId());
            
//             if (deleted) {
//                 response.sendRedirect(request.getContextPath() + "/transactions/");
//             } else {
//                 response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found or not owned by user");
//             }
//         } catch (NumberFormatException e) {
//             response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transaction ID");
//         }
//     }
    
//     private void handleAddTransaction(HttpServletRequest request, HttpServletResponse response, User user)
//             throws ServletException, IOException {
//         try {
//             Transaction transaction = createTransactionFromRequest(request, user);
            
//             if (transactionDAO.addTransaction(transaction)) {
//                 response.sendRedirect(request.getContextPath() + "/transactions/");
//             } else {
//                 request.setAttribute("error", "Failed to add transaction");
//                 request.getRequestDispatcher("/transactions/add.jsp").forward(request, response);
//             }
//         } catch (IllegalArgumentException e) {
//             request.setAttribute("error", e.getMessage());
//             request.getRequestDispatcher("/transactions/add.jsp").forward(request, response);
//         }
//     }
    
//     private Transaction createTransactionFromRequest(HttpServletRequest request, User user) {
//         double amount;
//         try {
//             amount = Double.parseDouble(request.getParameter("amount"));
//         } catch (NumberFormatException e) {
//             throw new IllegalArgumentException("Invalid amount format");
//         }
        
//         String type = request.getParameter("type");
//         String category = request.getParameter("category");
//         String description = request.getParameter("description");
//         String transactionDate = request.getParameter("transaction_date");
        
//         if (type == null || !(type.equals("income") || type.equals("expense"))) {
//             throw new IllegalArgumentException("Invalid transaction type");
//         }
        
//         if (category == null || category.trim().isEmpty()) {
//             throw new IllegalArgumentException("Category is required");
//         }
        
//         return new Transaction(
//             user.getUserId(),
//             amount,
//             type,
//             category,
//             description,
//             transactionDate
//         );
//     }
// }
