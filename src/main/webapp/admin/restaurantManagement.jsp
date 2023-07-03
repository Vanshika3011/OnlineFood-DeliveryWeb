<%@ page import="com.narola.finalproject.model.Restaurant" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restaurant Management</title>
    <h3 style="color: red">${error}</h3>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th:last-child, td:last-child {
            text-align: center;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        .edit-link, .delete-link, .view-link {
            padding: 6px 10px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            margin-right: 5px;
        }

        .delete-link {
            background-color: #f44336;
        }

        .image {
            width: 80px;
            height: 80px;
        }
    </style>
</head>
<body>
<jsp:include page="adminDashboard.jsp" />

<table>
    <tr>
        <th></th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    <%
        List<Restaurant> restaurantList = (List<Restaurant>) request.getAttribute("restaurantList");
        for (Restaurant restaurant : restaurantList) {
            String restaurantName = restaurant.getRestaurantName();
            int restaurantId = restaurant.getRestaurantId();
    %>
    <tr>
        <td><img class="image" src="<%= restaurant.getRestaurantImageUrl() %>" alt="<%= restaurantName %>"></td>
        <td><%= restaurantName%></td>
        <td>
            <a class="view-link" href="/viewRestaurant?id=<%= restaurantId %>">View</a>
            <a class="edit-link" href="/editRestaurant?id=<%= restaurantId %>">Edit</a>
        </td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>
