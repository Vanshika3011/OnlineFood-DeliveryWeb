<%@ page import="com.narola.finalproject.model.Restaurant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restaurant</title>
    <style>

        .container {
            display: flex;
            background-color: #fff3f3;
            padding: 20px;
        }

        .details {
            flex: 1;
        }

        .image {
            flex: 1;
            text-align: right;
        }

        .image img {
            max-width: 80%;
            max-height: 80%;

        }

        .restAdmin-tab a {
            text-decoration: none;
            color: #333;
            padding: 5px 10px;
        }

        .restAdmin-tab a:hover {
            background-color: #ead1dc;
        }

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

        .edit-link, .delete-link {
            padding: 6px 10px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            margin-right: 5px;
        }

        .delete-link {
            background-color: #f44336;
        }
    </style>
</head>
<body>
<% HttpSession httpSession = request.getSession();
   Restaurant restaurant = (Restaurant) httpSession.getAttribute("restaurant1");
%>
<jsp:include page="restaurantAdminDashboard.jsp"/>
<br>
<div class="container">
    <div class="details">
        <h2>Restaurant Details</h2>
        <p><strong>Name            :</strong> <%= restaurant.getRestaurantName() %></p>
        <p><strong>Contact         :</strong> <%= restaurant.getRestaurantContact() %></p>
        <p><strong>Email           :</strong> <%= restaurant.getRestaurantEmail() %></p>
        <p><strong>Restaurant Owner:</strong> <%= restaurant.getUser().getFirstName() + " " + restaurant.getUser().getLastName() %></p>
        <p><strong>Address         :</strong> <%= restaurant.getRestaurantAddress().getRestaurantAddLine1()  + ", "  +restaurant.getRestaurantAddress().getRestaurantAddLine2() %></p>
        <p><strong>City            :</strong> <%= restaurant.getRestaurantAddress().getCity().getCityName()%></p>
        <p><strong>State           :</strong> <%= restaurant.getRestaurantAddress().getState().getStateName() %></p>
        <p><strong>Pincode         :</strong> <%= restaurant.getRestaurantAddress().getPincode() %></p>
        <p><strong>Working Hours   :</strong> <%= restaurant.getOpensAt() +" am - "+restaurant.getClosesAt()+" pm"%></p>
        <p><strong>GST Number      :</strong> <%= restaurant.getGstNo() %></p>
        <p><strong>Dining Available:</strong> <%= ((restaurant.isDiningAvailable())?"Yes":"No")%></p>
        <p>
            <a class="edit-link" href="/editRestaurant?id=<%= restaurant.getRestaurantId() %>">Edit</a>
        </p>
    </div>
    <div class="image">
        <img src="<%= restaurant.getRestaurantImageUrl()%>" alt="<%= restaurant.getRestaurantName() %>">
    </div>
</div>
</body>
</html>
