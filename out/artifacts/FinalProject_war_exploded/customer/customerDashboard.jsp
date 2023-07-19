<%@ page import="com.narola.finalproject.model.User" %>
<%@ page import="com.narola.finalproject.service.CartService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% HttpSession httpsession = request.getSession();
    User user = (User) httpsession.getAttribute("user");
    CartService cartService = new CartService();
%>
<html>
<head>
    <link href="https://use.fontawesome.com/releases/v5.0.1/css/all.css" rel="stylesheet">
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

        .badge:after {
            content: attr(value);
            font-size: 18px;
            color: #fff;
            background: red;
            border-radius: 50%;
            padding: 0 5px;
            position: relative;
            left: -8px;
            top: -10px;
            opacity: 0.9;
        }
    </style>
</head>
<body>
<div class="customer-tab">
    <a href="/customerHomePage">Home</a>
    <a href="/addressPage">Address</a>
    <a href="/myOrders">Orders</a>
<%--    <a >My Cart</a>--%>
    <a href="/logout">Logout</a>
    <a href="/viewCartDetails" class="fa badge fa-lg" value=<%= cartService.getCartItemCount(user.getUserId())%>>&#xf07a;</a>
</div>
<br>
</body>
</html>
