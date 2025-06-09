<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page
import="com.financetracker.model.User" %> <% User user = (User)
session.getAttribute("user");%>
 <%@ page import = "java.io.*,java.util.*,java.sql.*"%> <%@ page import =
"javax.servlet.http.*,javax.servlet.*" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %> <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %> <%@
taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dashboard - Finance Tracker</title>
    <link rel="stylesheet" href="assets/css/style.css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
      SELECT * FROM transactions WHERE user_id = ?
      <sql:param value="${user.userId}" />
    </sql:query>
    <sql:query var="summaryResult" dataSource="${snapshot}">
      SELECT SUM(CASE WHEN type = 'income' THEN amount ELSE 0 END) AS
      total_income, SUM(CASE WHEN type = 'expense' THEN amount ELSE 0 END) AS
      total_expense FROM transactions WHERE user_id = ?
      <sql:param value="${user.userId}" />
    </sql:query>
    <header class="dashboard-header">
      <div class="container">
        <h1>Welcome, <%= user.getUsername() %></h1>
        <nav>
          <ul>
            <li>
              <a
                href="${pageContext.request.contextPath}/WEB-INF/views/dashboard.jsp"
                class="active"
                >Dashboard</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/list"
                >Transactions</a
              >
            </li>
               <li>
              <a href="${pageContext.request.contextPath}/fixedDeposit"
                >FD Calculator</a
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
      <section class="summary-cards">
        <div class="card income-card">
          <h3>Total Income</h3>
          <p id="total-income">&#36;${summaryResult.rows[0].total_income}</p>
        </div>

        <div class="card expense-card">
          <h3>Total Expenses</h3>
          <p id="total-expense">&#36;${summaryResult.rows[0].total_expense}</p>
        </div>

        <div class="card balance-card">
          <h3>Balance</h3>
          <p id="balance">
            &#36;${summaryResult.rows[0].total_income -
            summaryResult.rows[0].total_expense}
          </p>
        </div>
      </section>

      <section class="recent-transactions">
        <a
        href="<%= request.getContextPath() %>/add"
        class="btn btn-primary"
        >Add Transaction</a
      >
      <a
        href="<%= request.getContextPath() %>/budget"
        class="btn btn-primary"
        >Set Budget</a
      >
        <div class="visual">
          <div id="chart-container">
            <canvas id="categoryBarGraph" width="100" height="100"></canvas>
            <p>Income</p>
          </div>
          <div id="chart-container">
            <canvas id="expenseBarGraph" width="100" height="100"></canvas>
            <p>Expense</p>
        </div>
       
      </section>
    </main>

    <script src="assets/js/dashboard.js"></script>
  </body>
</html>
