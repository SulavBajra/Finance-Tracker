<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page
import="com.financetracker.model.User" %> <% User user = (User)
session.getAttribute("user"); if (user == null) {
response.sendRedirect(request.getContextPath() + "/auth/login.jsp"); return; }
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Add Transaction - Finance Tracker</title>
    <link
      rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/style.css"
    />
  </head>
  <body>
    <header class="dashboard-header">
      <div class="container">
        <h1>Add Transaction</h1>
        <nav>
          <ul>
            <li>
              <a href="${pageContext.request.contextPath}/login">Dashboard</a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/dashboard"
                >Transactions</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </li>
          </ul>
        </nav>
      </div>
    </header>

    <main class="container">
      <% if (request.getAttribute("error") != null) { %>
      <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
      <% } %>

      <form action="${pageContext.request.contextPath}/add" method="post">
        <div class="form-group">
          <label for="type">Type</label>
          <select id="type" name="type" required>
            <option value="">Select Type</option>
            <option value="income">Income</option>
            <option value="expense">Expense</option>
          </select>
        </div>

        <div class="form-group">
          <label for="amount">Amount</label>
          <input
            type="number"
            id="amount"
            name="amount"
            step="0.01"
            min="0"
            required
          />
        </div>

        <div class="form-group">
          <label for="category">Category</label>
          <select id="category" name="category" required>
            <option value="">Select Category</option>
          </select>
        </div>

        <div class="form-group">
          <label for="transactionDate">Date</label>
          <input
            type="date"
            id="transactionDate"
            name="transactionDate"
            required
          />
        </div>

        <div class="form-group">
          <label for="description">Description (Optional)</label>
          <textarea id="description" name="description" rows="3"></textarea>
        </div>

        <div class="form-actions">
          <button type="submit" class="btn btn-primary">Add Transaction</button>
          <a
            href="${pageContext.request.contextPath}/dashboard"
            class="btn btn-secondary"
            >Cancel</a
          >
        </div>
      </form>
    </main>

    <script src="<%=request.getContextPath()%>/assets/js/add-transaction.js"></script>
    <script>
      // Set default date to today
      const today = new Date().toISOString().split("T")[0];
      document.getElementById("transactionDate").value = today;
      console.log("Default date set to today:", today);
    </script>
  </body>
</html>
