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
          href="${pageContext.request.contextPath}/list"
          class="btn btn-primary"
          >Back to Transactions</a
        >
      </header>

      <section class="transaction-details">
        <c:choose>
          <c:when test="${not empty transactions}">
            <c:forEach var="transaction" items="${transactions}">
              <div class="transaction-card">
                <p><strong>ID:</strong> ${transaction.transaction_id}</p>
                <p>
                  <strong>Amount:</strong>
                  <fmt:formatNumber
                    value="${transaction.amount}"
                    type="currency"
                  />
                </p>
                <p>
                  <strong>Date:</strong>
                  <!-- <fmt:formatDate
                    value="${transaction.transaction_date}"
                    pattern="yyyy-MM-dd"
                  /> -->
                </p>
                <p><strong>Type:</strong> ${transaction.type}</p>
                <p><strong>Category:</strong> ${transaction.category}</p>
                <p><strong>Description:</strong> ${transaction.description}</p>
              </div>
              <hr />
            </c:forEach>
          </c:when>
          <c:otherwise>
            <p>No transaction details available.</p>
          </c:otherwise>
        </c:choose>
      </section>
    </main>
  </body>
</html>
