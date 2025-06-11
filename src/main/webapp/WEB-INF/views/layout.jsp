<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page
import="com.financetracker.model.User" %> <% User user = (User)
request.getAttribute("user"); %>

<header class="dashboard-header">
  <div class="container">
    <h1>Welcome, ${user.username}</h1>
    <nav>
      <ul>
        <li>
          <a
            href="${pageContext.request.contextPath}/dashboard"
            class="${currentUri != null && currentUri.contains('/dashboard') ? 'active' : ''}"
          >
            Dashboard
          </a>
        </li>
        <li>
          <a
            href="${pageContext.request.contextPath}/list"
            class="${currentUri != null && currentUri.contains('/list') ? 'active' : ''}"
          >
            Transactions
          </a>
        </li>
        <li>
          <a
            href="${pageContext.request.contextPath}/fixedDeposit"
            class="${currentUri != null && currentUri.contains('/fixedDeposit') ? 'active' : ''}"
          >
            FD Calculator
          </a>
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
