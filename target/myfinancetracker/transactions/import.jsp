<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page
import="com.financetracker.model.User" %> <% User user = (User)
session.getAttribute("user"); if (user == null) {
response.sendRedirect(request.getContextPath() + "/auth/login.jsp"); return; }
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      rel="stylesheet"
      href="<%= request.getContextPath() %>/assets/css/pop.css"
    />
    <title>Import Transactions</title>
  </head>
  <body>
    <div class="box">
      <div class="container">
        <h1 class="title">Import Transactions</h1>
        <form
          action="<%= request.getContextPath() %>/import"
          method="post"
          enctype="multipart/form-data"
          class="form"
        >
          <div class="form-group">
            <label for="file" class="form-label">Upload File:</label>
            <input
              type="file"
              id="file"
              name="file"
              accept=".csv, .xlsx"
              class="form-input"
              required
            />
          </div>
          <button type="submit" class="btn btn-primary">Import</button>
          <div class="btn btn-primary">
            <a href="${pageContext.request.contextPath}/transactions/list.jsp"
              >Cancel</a
            >
          </div>
        </form>
      </div>
    </div>
  </body>
</html>
