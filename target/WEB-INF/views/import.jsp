<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <style>
      .btn {
        text-decoration: none;
      }
      .btn-primary {
        display: inline-block;
        padding: 0.5rem 1rem;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.2s;
      }
      .btn-primary:hover {
        background-color: #0056b3;
      }
      .btn-primary a {
        text-decoration: none;
        color: white;
      }
    </style>
  </head>
  <body>
    <div class="box">
      <div class="container">
        <h1 class="title">Import Transactions</h1>
        <!-- Display Error Message -->
        <c:if test="${not empty error}">
          <div class="alert alert-error">
            <i class="fas fa-exclamation-circle"></i> ${error}
          </div>
        </c:if>
        <form
          action="<%= request.getContextPath() %>/import"
          method="post"
          enctype="multipart/form-data"
          class="form"
          onsubmit="return validateFileType()"
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
          <a href="<%= request.getContextPath() %>/list" class="btn btn-primary"
            >Cancel</a
          >
        </form>
      </div>
    </div>

    <script>
      function validateFileType() {
        const fileInput = document.getElementById("file");
        const filePath = fileInput.value;
        const allowedExtensions = /(\.csv|\.xlsx)$/i;

        if (!allowedExtensions.exec(filePath)) {
          alert("Please upload a valid .csv or .xlsx file.");
          fileInput.value = "";
          return false;
        }
        return true;
      }
    </script>
  </body>
</html>
