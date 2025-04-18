<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page
import="com.financetracker.model.User" %> <%@ page import =
"java.io.*,java.util.*,java.sql.*"%> <%@ page import =
"javax.servlet.http.*,javax.servlet.*" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %> <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %> <%@
taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Edit Transaction - Finance Tracker</title>
    <link
      rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/style.css"
    />
  </head>
  <body>
    <sql:setDataSource
      var="snapshot"
      driver="com.mysql.cj.jdbc.Driver"
      url="jdbc:mysql://localhost/finance_tracker"
      user="admin"
      password="admin123"
    />

    <sql:query var="transactions" dataSource="${snapshot}">
      SELECT * FROM transactions where transaction_id=? AND user_id = ?
      <sql:param value="${param.id}" />
      <sql:param value="${sessionScope.user.userId}" />
    </sql:query>
    <header class="dashboard-header">
      <div class="container">
        <h1>Add Transaction</h1>
        <nav>
          <ul>
            <li>
              <a href="${pageContext.request.contextPath}/dashboard"
                >Dashboard</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/list">Transactions</a>
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

      <form action="${pageContext.request.contextPath}/edit" method="post">
        <div class="form-group">
          <label for="type">Type</label>
          <select id="type" name="type" required>
            <option value="">${transactions.rows[0].type}</option>
            <option value="income">Income</option>
            <option value="expense">Expense</option>
          </select>
        </div>
        <input
          type="hidden"
          name="id"
          value="${transactions.rows[0].transaction_id}"
        />
        <div class="form-group">
          <label for="amount">Amount</label>
          <input
            type="number"
            id="amount"
            name="amount"
            step="0.01"
            min="0"
            value="${transactions.rows[0].amount}"
            required
          />
        </div>

        <div class="form-group">
          <label for="category">Category</label>
          <select id="category" name="category" required>
            <option value="">${transactions.rows[0].category}</option>
          </select>
        </div>

        <div class="form-group">
          <label for="transactionDate">Date</label>
          <input
            type="date"
            id="transactionDate"
            name="transactionDate"
            value="${transactions.rows[0].transaction_date}"
            required
          />
        </div>

        <div class="form-group">
          <label for="description">Description (Optional)</label>
          <textarea id="description" name="description" rows="3">
${transactions.rows[0].description}</textarea
          >
        </div>

        <div class="form-actions">
          <button type="submit" class="btn btn-primary">
            Edit Transaction
          </button>
          <a
            href="${pageContext.request.contextPath}/list"
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
