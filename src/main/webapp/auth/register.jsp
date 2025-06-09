<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Register - Finance Tracker</title>
    <link
      rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/style.css"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
    />
  </head>
  <body>
    <div class="auth-container">
      <div class="auth-card">
        <div class="auth-back">
          <span
            ><a href="<%=request.getContextPath()%>/index.jsp"
              ><i class="fa fa-arrow-left"></i> </a
          ></span>
          <h2>Register</h2>
        </div>

        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger">
          <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form
          action="${pageContext.request.contextPath}/register"
          method="post"
          onsubmit="return validateForm()"
        >
          <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required />
          </div>

          <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required />
          </div>

          <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required />
          </div>

          <div class="form-group">
            <label for="repassword">Confirm Password</label>
            <input type="password" id="password" name="repassword" required />
          </div>

          <button type="submit" class="btn btn-primary">Register</button>
        </form>

        <div class="auth-footer">
          Already have an account?
          <a href="<%=request.getContextPath()%>/auth/login.jsp">Login here</a>
        </div>
      </div>
    </div>

    <script src="<%=request.getContextPath()%>//assets/js/auth.js"></script>
  </body>
</html>
