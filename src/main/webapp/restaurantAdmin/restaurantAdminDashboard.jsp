
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        .restAdmin-tab {
            display: flex;
            justify-content: space-around;
            align-items: center;
            background-color: #fff3f3;
            padding: 10px;
        }

        .restAdmin-tab a {
            text-decoration: none;
            color: #333;
            padding: 5px 10px;
        }

        .restAdmin-tab a:hover {
            background-color: #FFC7C7;
        }
    </style>
</head>
<body>
<div class="restAdmin-tab">
    <a href="/manageRestaurant">Restaurant</a>
    <a href="/menuManager">Menu</a>
    <a href="/viewOrders">Orders</a>
    <a href="/logout">Logout</a>
</div>
<br>
</body>
</html>
