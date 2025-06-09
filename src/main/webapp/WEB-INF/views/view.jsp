<!-- <%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%> <%@ page
import="com.financetracker.model.User" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/view.css"
    />
    <title>Details</title>
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
    <div class="detail">
      <div class="detail-window">
        <div class="detail-titlebar">
          <span class="detail-title">Transaction Details</span>
          <button class="detail-close" onclick="checkFalse()">&times;</button>
        </div>
        <div class="detail-content">
          <c:choose>
            <c:when test="${not empty transactions}">
              <p><strong>Transaction ID:</strong> ${transactions.id}</p>
              <p><strong>Amount:</strong> ${transactions.amount}</p>
              <p><strong>Date:</strong> ${transactions.date}</p>
            </c:when>
            <c:otherwise>
              <p>No transactions found.</p>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div> -->
<!-- <script src="<%=request.getContextPath()%>/assets/js/view.js
    "></script> -->
<!-- </body>
</html> -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Transaction Details</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/view.css"
    />
  </head>
  <body>
    <main class="container">
      <header>
        <h1>Transaction Details</h1>
        <a
          href="${pageContext.request.contextPath}/transactions"
          class="btn btn-primary"
          >Back to Transactions</a
        >
      </header>

      <section class="transaction-details">
        <c:choose>
          <c:when test="${transaction != null}">
            <p><strong>ID:</strong> ${transaction.id}</p>
            <p>
              <strong>Amount:</strong>
              <fmt:formatNumber value="${transaction.amount}" type="currency" />
            </p>
            <p><strong>Date:</strong> ${transaction.transactionDate}</p>
            <p><strong>Type:</strong> ${transaction.type}</p>
            <p><strong>Category:</strong> ${transaction.category}</p>
            <p><strong>Description:</strong> ${transaction.description}</p>
          </c:when>
          <c:otherwise>
            <p>No transaction details available.</p>
          </c:otherwise>
        </c:choose>
      </section>
    </main>
  </body>
</html>
