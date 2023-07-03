<%@ page import="java.util.List" %>
<%@ page import="com.narola.finalproject.model.CartDetails" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        .cart-item-actions {
            display: flex;
            align-items: center;
            height: 100px;
        }

        .cart-item-actions a, .cart-item-actions button {
            margin-left: 10px;
            padding: 5px 10px;
            border: none;
            background-color: #F9dfa2;
            color: #333;
            cursor: pointer;
            text-decoration: none;
        }

        .cart-item-actions a:hover, .cart-item-actions button:hover {
            background-color: #333;
            color: #F9dfa2;
        }

        .image {
            height: 100px;
            width: 100px;
        }

        .buttons {
            display: inline-block;
            padding: 5px 10px;
            background-color: #F9dfa2;
            text-decoration: none;
            color: #333;
        }

        .buttons:hover {
            background-color: #45a049;
        }

        .text-container {
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>
<body>
<jsp:include page="customerDashboard.jsp"/>
<h1>Your Cart Details</h1>
<% CartDetails cartDetails = (CartDetails) request.getAttribute("cartDetails");%>
<br>
<br>
<h2> Restaurant Name : <%= cartDetails.getRestaurantName()%>
</h2>
<table>
    <tr>
        <th></th>
        <th></th>
        <th>Item Name</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Actions</th>
    </tr>
    <%
        List<CartDetails> cartDetailsList = (List<CartDetails>) request.getAttribute("cartDetailsList");
        for (CartDetails cartItemDetails : cartDetailsList) {
            int itemId = cartItemDetails.getItemId();
    %>
    <tr>
        <td></td>
        <td><img class="image" src="<%= cartItemDetails.getImageUrl() %>" alt="<%= cartItemDetails.getItemName() %>">
        </td>
        <td><%= cartItemDetails.getItemName() %>
        </td>
        <td>
            <%= cartItemDetails.getPrice() %>

        </td>
        <td><a class="buttons" href="/decreaseItemCount?itemId=<%= itemId%>">-</a>
            <%= cartItemDetails.getQuantity() %>
            <a class="buttons" href="/increaseItemCount?itemId=<%= itemId%>">+</a>
        </td>
        <td class="cart-item-actions">
            <a href="/removeItem?itemId=<%= itemId%>">Remove</a>
        </td>
    </tr>
    <%
        }
    %>
</table>

<div class="text-container" >
    <p> Total amount : <%= cartDetails.getTotalPrice()%></p>
</div>
<div class="cart-item-actions" style="display: flex; justify-content: center; align-items: center;">
    <a href="/addressPage">Select Address</a>
</div>

</body>
</html>
