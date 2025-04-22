<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login - Finance Tracker</title>
    <link
      rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/style.css"
    />
  </head>
  <body>
    <div class="auth-container">
      <div class="auth-card">
        <h2>Login</h2>

        <% if (request.getParameter("registration") != null) { %>
        <div class="alert alert-success">
          Registration successful! Please login.
        </div>
        <% } %> <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger">
          <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="post">
          <div class="form-group">
            <label for="username">Email</label>
            <input type="text" id="username" name="email" required />
          </div>

          <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required />
          </div>

          <button type="submit" class="btn btn-primary">Login</button>
        </form>

        <div class="auth-footer">
          Don't have an account?
          <a href="<%=request.getContextPath()%>/auth/register.jsp"
            >Register here</a
          >
        </div>
      </div>
    </div>

    <script src="<%=request.getContextPath()%>/assets/js/transactions.js"></script>
  </body>
</html>
