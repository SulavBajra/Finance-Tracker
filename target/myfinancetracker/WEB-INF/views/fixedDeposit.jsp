<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.DecimalFormat" %>
<%
    // Variables to hold input and result
    String principalStr = request.getParameter("principal");
    String rateStr = request.getParameter("rate");
    String timeStr = request.getParameter("time");
    String frequencyStr = request.getParameter("frequency");

    double principal = 0, rate = 0, time = 0;
    int frequency = 1; // Default yearly compounding
    double maturityAmount = 0;

    boolean calculated = false;
    String error = null;

    if (principalStr != null && rateStr != null && timeStr != null) {
        try {
            principal = Double.parseDouble(principalStr);
            rate = Double.parseDouble(rateStr) / 100; // Convert percentage to decimal
            time = Double.parseDouble(timeStr);

            // Determine compounding frequency per year
            if ("yearly".equals(frequencyStr)) frequency = 1;
            else if ("half-yearly".equals(frequencyStr)) frequency = 2;
            else if ("quarterly".equals(frequencyStr)) frequency = 4;
            else if ("monthly".equals(frequencyStr)) frequency = 12;

            // FD maturity formula: A = P(1 + r/n)^(nt)
            maturityAmount = principal * Math.pow(1 + rate / frequency, frequency * time);
            calculated = true;
        } catch (NumberFormatException e) {
            error = "Please enter valid numeric values.";
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Fixed Deposit Calculator</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fd.css" />
     <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
    />
</head>
<body>
    <main class="container">
        <span>
            <a href="${pageContext.request.contextPath}/dashboard"
              ><i class="fa fa-arrow-left"></i> </a
          ></span>
        <h1>Fixed Deposit Calculator</h1>
        <form method="get" action="${pageContext.request.contextPath}/fixedDeposit">
            <label for="principal">Principal Amount:</label>
            <input type="text" id="principal" name="principal" value="<%= principalStr != null ? principalStr : "" %>" required />

            <label for="rate">Annual Interest Rate (%):</label>
            <input type="text" id="rate" name="rate" value="<%= rateStr != null ? rateStr : "" %>" required />

            <label for="time">Tenure (years):</label>
            <input type="text" id="time" name="time" value="<%= timeStr != null ? timeStr : "" %>" required />

            <label for="frequency">Compounding Frequency:</label>
            <select id="frequency" name="frequency">
                <option value="yearly" <%= "yearly".equals(frequencyStr) ? "selected" : "" %>>Yearly</option>
                <option value="half-yearly" <%= "half-yearly".equals(frequencyStr) ? "selected" : "" %>>Half-Yearly</option>
                <option value="quarterly" <%= "quarterly".equals(frequencyStr) ? "selected" : "" %>>Quarterly</option>
                <option value="monthly" <%= "monthly".equals(frequencyStr) ? "selected" : "" %>>Monthly</option>
            </select>

            <button type="submit" class="btn btn-primary">Calculate</button>
        </form>

        <hr/>

        <% if (error != null) { %>
            <p class="error"><%= error %></p>
        <% } else if (calculated) {
            DecimalFormat df = new DecimalFormat("#,##0.00");
        %>
            <h2>Results</h2>
            <p>Principal: Rs<%= df.format(principal) %></p>
            <p>Interest Rate: <%= (rate * 100) %>% per annum</p>
            <p>Tenure: <%= time %> years</p>
            <p>Compounding Frequency: <%= frequencyStr %></p>
            <p><strong>Maturity Amount: Rs<%= df.format(maturityAmount) %></strong></p>
        <% } %>
    </main>
</body>
</html>
