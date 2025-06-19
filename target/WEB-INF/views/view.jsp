<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>View Transaction</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/view.css"
    />
  </head>
  <body>
    <c:if test="${not empty error}">
      <p style="color: red">${error}</p>
    </c:if>

    <c:if test="${not empty transaction}">
      <h2>Transaction Details</h2>
      <table>
        <tr>
          <th>Username</th>
          <td>${user.username}</td>
        </tr>
        <tr>
          <th>Amount</th>
          <td>${transaction.amount}</td>
        </tr>
        <tr>
          <th>Type</th>
          <td>${transaction.type}</td>
        </tr>
        <tr>
          <th>Category</th>
          <td>${transaction.category}</td>
        </tr>
        <tr>
          <th>Description</th>
          <td><c:out value="${transaction.description}" default="-" /></td>
        </tr>
        <tr>
          <th>Transaction Date</th>
          <td>${transaction.transactionDate}</td>
        </tr>
      </table>
    </c:if>
  </body>
</html>
