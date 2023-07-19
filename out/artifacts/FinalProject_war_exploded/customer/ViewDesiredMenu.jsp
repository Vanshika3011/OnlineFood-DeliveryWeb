<%@ page import="com.narola.finalproject.model.RestaurantMenu" %>
<%@ page import="java.util.List" %>
<%@ page import="com.narola.finalproject.model.Restaurant" %>
<html>
<head>
    <!-- CSS styles -->
    <style>
        body {
            font-family: "Times New Roman", sans-serif;
            margin: 20px;
        }

        .menu-items-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
        }

        .menu-item {
            width: 30%;
            display: flex;
            flex-direction: column;
            align-items: center;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 20px;
            margin-bottom: 20px;
            text-align: center;
        }

        .menu-item img {
            max-width: 200px;
            max-height: 150px;
            margin-bottom: 10px;
        }

        .menu-item-details {
            margin-bottom: 10px;
        }

        .menu-item-details h2 {
            color: #333;
            margin: 0;
        }

        .menu-item-details label {
            font-weight: bold;
        }

        .menu-item-details span {
            margin-right: 10px;
        }

        .add-to-cart-link {
            text-decoration: none;
        }

        .add-to-cart-button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<jsp:include page="customerDashboard.jsp"/>

<div class="menu-items-container">
    <%
        List<Restaurant> restaurantList = (List<Restaurant>) request.getAttribute("restaurantList");

        for (Restaurant restaurant : restaurantList) {
    %>
    <div class="menu-item">
        <img src="<%= restaurant.getRestaurantImageUrl() %>" alt="<%= restaurant.getRestaurantName() %>">
        <div class="menu-item-details">
            <h2><%=restaurant.getRestaurantName() %>
            </h2>
            <label>Address:</label>
            <span><%= restaurant.getRestaurantAddress().getRestaurantAddLine1() + ", " + restaurant.getRestaurantAddress().getRestaurantAddLine2()  %></span>
            <span><%= restaurant.getRestaurantAddress().getCity().getCityName() + " ," + restaurant.getRestaurantAddress().getPincode()%></span>
            <span><%= restaurant.getRestaurantAddress().getState().getStateName()%></span>
            <br>
            <label>Contact:</label>
            <span><%= restaurant.getRestaurantContact() %></span>
            <br>
            <label>Email</label>
            <span><%= restaurant.getRestaurantEmail()%></span>
            <br>
            <label>Dine In:</label>
            <span><%= restaurant.isDiningAvailable() ? "Yes" : "No" %></span>
            <br>
            <div>
                <a href="/viewRestaurantMenu?id=<%=restaurant.getRestaurantId()%>" class="add-to-cart-link">
                    <button class="add-to-cart-button">View Menu</button>
                </a>
            </div>
        </div>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
