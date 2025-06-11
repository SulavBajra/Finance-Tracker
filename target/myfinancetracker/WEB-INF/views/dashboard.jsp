<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page
import="com.financetracker.model.User" %><% User user = (User)
request.getAttribute("user"); %> <%@ page import =
"java.io.*,java.util.*,java.sql.*"%> <%@ page import =
"javax.servlet.http.*,javax.servlet.*" %><%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dashboard - Finance Tracker</title>
    <link rel="stylesheet" href="assets/css/style.css" />
    <link rel="stylesheet" href="assets/css/dashboard.css" />

    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/pop.css"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
    />
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
        <h1>Welcome, ${user.username}</h1>
        <nav>
          <ul>
            <li>
              <a
                href="${pageContext.request.contextPath}/dashboard"
                class="active"
                >Dashboard</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/list">Transactions</a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/fixedDeposit"
                >FD Calculator</a
              >
            </li>
            <li>
              <form
                action="${pageContext.request.contextPath}/logout"
                method="get"
                onsubmit="return pop(event)"
              >
                <button type="submit">Logout</button>
              </form>
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
          <c:choose>
            <c:when test="${not empty status and status}">
              <h3 class="balance-heading">Balance</h3>
              <p id="balance">
                &#36;${summaryResult.rows[0].total_income != null ?
                summaryResult.rows[0].total_income : 0 -
                summaryResult.rows[0].total_expense != null ?
                summaryResult.rows[0].total_expense : 0}
              </p>
              <span class="warning-message"
                ><i class="fa-solid fa-triangle-exclamation"></i>Warning</span
              >
            </c:when>
            <c:otherwise>
              <h3>Balance</h3>
              <p id="balance">
                &#36;${summaryResult.rows[0].total_income != null ?
                summaryResult.rows[0].total_income : 0 -
                summaryResult.rows[0].total_expense != null ?
                summaryResult.rows[0].total_expense : 0}
              </p>
            </c:otherwise>
          </c:choose>
        </div>
      </section>

      <section class="recent-transactions">
        <a href="<%= request.getContextPath() %>/add" class="btn btn-primary"
          >Add Transaction</a
        >
        <a href="<%= request.getContextPath() %>/budget" class="btn btn-primary"
          >Set Budget</a
        >
        <div class="visual">
          <div id="chart-container">
            <canvas id="categoryBarGraph"></canvas>
            <p>Income</p>
          </div>
          <div id="chart-container">
            <canvas id="expenseBarGraph"></canvas>
            <p>Expense</p>
          </div>
        </div>
      </section>
      <div class="confirm" style="display: none">
        <div class="confirm--window">
          <div class="confirm--titlebar">
            <span class="confirm--title">Confirmation</span>
            <button class="confirm--close" onclick="checkFalse()">
              &times;
            </button>
          </div>
          <div class="confirm--content">Are you sure you want to logout</div>
          <div class="confirm--buttons">
            <button
              class="confirm--button confirm--button--ok confirm--button--fill"
            >
              Yes
            </button>
            <button class="confirm--button confirm--button--cancel">No</button>
          </div>
        </div>
      </div>
    </main>

    <script src="assets/js/dashboard.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/popup.js"></script>
  </body>
</html>
