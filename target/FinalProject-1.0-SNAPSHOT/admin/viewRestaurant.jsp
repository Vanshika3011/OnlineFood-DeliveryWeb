<%@ page import="com.narola.finalproject.model.Restaurant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restaurant Details</title>
    <style>

        .container {
            display: flex;
            background-color: #f2f2f2;
            padding: 40px;
        }

        .details {
            flex: 1;
            text-align: justify;
            line-height: 1.5;
            font-size: large;
            font-family: "Times New Roman", sans-serif;
        }

        .image {
            flex: 1;
            text-align: right;
        }

        .image img {
            max-width: 80%;
            max-height: 80%;

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
    </style>
</head>
<body>
<jsp:include page="adminDashboard.jsp" />
<%
    Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
%>
<br>
<div class="container">
    <div class="details">
        <h2>Restaurant Details</h2>
        <div>
            <label><strong>Name:</strong></label>
            <span><%= restaurant.getRestaurantName() %></span>
        </div>
        <div>
            <label><strong>Contact:</strong></label>
            <span><%= restaurant.getRestaurantContact() %></span>
        </div>
        <div>
            <label><strong>Email:</strong></label>
            <span><%= restaurant.getRestaurantEmail() %></span>
        </div>
        <div>
            <label><strong>Restaurant Owner:</strong></label>
            <span><%= restaurant.getUser().getFirstName() + " " + restaurant.getUser().getLastName() %></span>
        </div>
        <div>
            <label><strong>Address:</strong></label>
            <span><%= restaurant.getRestaurantAddress().getRestaurantAddLine1()  + ", "  +restaurant.getRestaurantAddress().getRestaurantAddLine2() %></span>
        </div>
        <div>
            <label><strong>City:</strong></label>
            <span><%= restaurant.getRestaurantAddress().getCity().getCityName() %></span>
        </div>
        <div>
            <label><strong>State:</strong></label>
            <span><%= restaurant.getRestaurantAddress().getState().getStateName() %></span>
        </div>
        <div>
            <label><strong>Pincode:</strong></label>
            <span><%= restaurant.getRestaurantAddress().getPincode() %></span>
        </div>
        <div>
            <label><strong>Working Hours:</strong></label>
            <span><%= restaurant.getOpensAt() +" am - "+restaurant.getClosesAt()+" pm"%></span>
        </div>
        <div>
            <label><strong>GST Number:</strong></label>
            <span><%= restaurant.getGstNo() %></span>
        </div>
        <div>
            <label><strong>Dining Available:</strong></label>
            <span><%= ((restaurant.isDiningAvailable()) ? "Yes" : "No") %></span>
        </div>

    </div>
    <div class="image">
        <img src="<%= restaurant.getRestaurantImageUrl()%>" alt="<%= restaurant.getRestaurantName() %>">
    </div>
</div>
</body>
</html>
