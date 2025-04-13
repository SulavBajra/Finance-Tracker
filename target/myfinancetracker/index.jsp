<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Finance Tracker</title>
    <link rel="stylesheet" href="assets/css/style.css" />
  </head>
  <body>
    <header>
      <div class="container">
        <h1>Finance Tracker</h1>
        <p>Track your income and expenses easily</p>
      </div>
    </header>

    <main class="container">
      <section class="hero">
        <h2>Take Control of Your Finances</h2>
        <p>Simple, intuitive way to track your money flow</p>
        <div class="cta-buttons">
          <a
            href="<%= request.getContextPath() %>/auth/login.jsp"
            class="btn btn-primary"
            >Login</a
          >
          <a href="auth/register.jsp" class="btn btn-secondary">Register</a>
        </div>
      </section>
    </main>

    <footer>
      <div class="container">
        <p>&copy; 2023 Finance Tracker. All rights reserved.</p>
      </div>
    </footer>

    <script src="assets/js/main.js"></script>
  </body>
</html>
