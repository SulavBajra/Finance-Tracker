<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Set Budget</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/budget.css"
    />
  </head>
  <body>
    <main class="container">
      <h1>Set Your Budget</h1>
      <form method="post" action="${pageContext.request.contextPath}/budget">
        <label for="amount">Budget Amount:</label>
        <input
          type="number"
          id="amount"
          name="budgetAmount"
          placeholder="Enter budget amount"
          required
        />

        <label for="type">Budget Type:</label>
        <select id="type" name="period">
          <option value="monthly">Monthly</option>
          <option value="yearly">Yearly</option>
        </select>

        <label for="start-date">Start Date:</label>
        <input type="date" id="start-date" name="startDate" required />

        <button type="submit" class="btn btn-primary">Set Budget</button>
      </form>

      <div>
        <a
          href="<%= request.getContextPath() %>/dashboard"
          class="btn btn-secondary"
          >Back to Dashboard</a
        >
      </div>
    </main>
  </body>
</html>
