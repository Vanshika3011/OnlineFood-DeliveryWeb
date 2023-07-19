<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        .admin-tab {
            display: flex;
            justify-content: space-around;
            align-items: center;
            background-color: #f1f1f1 ;
            padding: 10px;
        }

        .admin-tab a {
            text-decoration: none;
            color: #000000;
            padding: 5px 10px;
        }

        .admin-tab a:hover {
            background-color: #ddd;
        }
    </style>
</head>
<body>
<div class="admin-tab">
    <a href="/adminHomePage">Home</a>
    <a href="/userManagement">User Management</a>
    <a href="/restaurantManagement">Restaurant Management</a>
    <a href="/cuisineManagement">Cuisine Management</a>
    <a href="/logout">Logout</a>
</div>
<br>
</body>
</html>
