<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ page import="com.financetracker.model.User" %> 
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<!-- <% User user = (User)
session.getAttribute("user"); if (user == null) {
response.sendRedirect(request.getContextPath() + "/auth/login.jsp"); return; } %> -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/transactions.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/pop.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <sql:setDataSource var = "snapshot" driver="com.mysql.cj.jdbc.Driver"
    url = "jdbc:mysql://localhost/finance_tracker"
    user = "admin"  password = "admin123"/>

    <sql:query var="transactions" dataSource="${snapshot}">
        SELECT * FROM transactions where user_id = ? order by transaction_date desc
        <sql:param value="${sessionScope.user.userId}"/>
    </sql:query>
   
    <main class="container">
        
        <header class="page-header">
            <h1 class="prev"><a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">Dashboard</a></h1>
            <h1><i class="fas fa-exchange-alt"></i> Transaction History</h1>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <i class="fas fa-exclamation-circle"></i> ${error}
                </div>
            </c:if>
            
            <div class="activity-bar"><a href="${pageContext.request.contextPath}/add" class="btn btn-primary">
                <i class="fas fa-plus"></i> New Transaction
            </a>
            <a href="${pageContext.request.contextPath}/export" class="btn btn-primary">Export to CSV</a>
            <a href="${pageContext.request.contextPath}/import" class="btn btn-primary">Import from CSV</a>
            <div>
                <form method="get" action="${pageContext.request.contextPath}/search">
                    <input type="text" name="query" placeholder="Search transactions..." class="search-input">
                    <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i></button>
                </form>
            </div>
        </div>
        </header>
        <section class="transaction-list">
            <c:choose>
                <c:when test="${empty transactions.rows}">
                    <div class="empty-state">
                        <i class="fas fa-wallet"></i>
                        <h3>No Transactions Found</h3>
                        <p>Get started by adding your first transaction:</p>
                        <a href="${pageContext.request.contextPath}/add.jsp" class="btn btn-primary">
                            <i class="fas fa-plus"></i> Add Transaction
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="transaction-table">
                            <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Type</th>
                                    <th>Category</th>
                                    <th class="text-right">Amount</th>
                                    <th>Edit</th>
                                    <!-- <th>Description</th> -->
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${transactions.rows}" var="txn">
                                    <tr class="transaction-${txn.type}">
                                        <td>${txn.transaction_date}</td>
                                        <td>
                                            <span class="badge badge-${txn.type}">
                                                ${txn.type}
                                            </span>
                                        </td>
                                        <td>${txn.category}</td>
                                        <td class="text-right amount-${txn.type}">
                                            <fmt:formatNumber value="${txn.amount}" 
                                                type="currency" 
                                                currencySymbol="${txn.type == 'income' ? '+' : '-'}$"/>
                                        </td>
                                        <!-- <td class="description">${txn.description}</td> -->
                                         <td>
                                            <form method="get" action="${pageContext.request.contextPath}/edit" onsubmit="return pop(event)">
                                            <input type="hidden" name="id" value="${txn.transaction_id}">    
                                            <button type="submit" class="btn btn-icon btn-good" 
                                            title="Edit Transaction"><i class='fas fa-plus' ></i></button>
                                        </form></td>
                                        <td class="actions">
                                            <form method="post" 
                                                  action="${pageContext.request.contextPath}/delete"
                                                  onsubmit="return pop(event)">
                                                <input type="hidden" name="id" value="${txn.transaction_id}">
                                                <button type="submit" id="deletion" class="btn btn-icon btn-danger" 
                                                        title="Delete Transaction">
                                                    <i class="fas fa-trash"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
    </main>
    <div class="confirm" style="display: none;">
        <div class="confirm--window">
            <div class="confirm--titlebar">
                <span class="confirm--title">Confirmation</span>
                <button class="confirm--close" onclick="checkFalse()">&times;</button>
            </div>
            <div class="confirm--content">Are you sure you want to make changes</div>
            <div class="confirm--buttons">
                <button class="confirm--button confirm--button--ok confirm--button--fill">
                    Yes
                </button>
                <button class="confirm--button confirm--button--cancel">No</button>
            </div>
        </div>
    </div>
    
    <script src="<%=request.getContextPath()%>/assets/js/popup.js
    "></script>
</body>
</html> 

