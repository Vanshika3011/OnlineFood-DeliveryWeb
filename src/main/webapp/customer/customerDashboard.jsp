<%@ page import="com.narola.finalproject.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% HttpSession httpsession = request.getSession();
User user = (User) httpsession.getAttribute("user");
%>
<html>
<head>
    <style>
        .dashboard h1 {
            color: #333;
            font-size: 24px;
            margin-bottom: 10px;
        }

        .customer-tab {
            display: flex;
            justify-content: space-around;
            align-items: center;
            background-color: #F9dfa2;
            padding: 10px;
        }

        .customer-tab a {
            text-decoration: none;
            color: #333;
            padding: 5px 10px;
        }

        .customer-tab a:hover {
            background-color: #F1CF7F;
        }

    </style>
</head>
<body>
<div class="customer-tab">
    <a href="/customerHomePage">Home</a>
    <a href="/addressPage">Address</a>
    <a href="orders.jsp">Orders</a>
    <a href="/viewCartDetails">My Cart</a>
    <a href="/logout">Logout</a>
</div>
<br>
</body>
</html>
