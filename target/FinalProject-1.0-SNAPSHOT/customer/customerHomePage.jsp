<%@ page import="com.narola.finalproject.model.CuisineCategory" %>
<%@ page import="java.util.List" %>
<%@ page import="com.narola.finalproject.model.RestaurantMenu" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <style>
        .cuisine-filter-item {
            display: inline-block;
            margin-right: 10px;
            text-align: center;
        }

        .cuisine-filter-item img {
            width: 180px;
            height: 180px;
            border-radius: 50%;
            border: 2px solid #ccc;
            transition: border-color 0.3s ease;
        }

        .cuisine-filter-item img:hover {
            border-color: #333;
        }

        .cuisine-filter-item .cuisine-name {
            font-weight: bold;
            margin-top: 10px;
        }


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
    <script>
        function filterByCuisine(cuisineId) {
            var url = "/viewItems?cuisineId=" + cuisineId;
            window.location.href = url;
        }
    </script>
</head>
<body>
<jsp:include page="customerDashboard.jsp"/>
<br>
<h4>WHAT'S ON YOUR MIND?</h4><br>
<div class="cuisine-filter">
    <% List<CuisineCategory> cuisines = (List<CuisineCategory>) request.getAttribute("cuisineCategoryList");
        for (CuisineCategory cuisine : cuisines) { %>
    <div class="cuisine-filter-item" onclick="filterByCuisine(<%= cuisine.getCuisineId()%>)">
        <img src="<%= cuisine.getCuisineImageURL() %>" alt="<%= cuisine.getCuisineName() %>">
        <div class="cuisine-name"> <%= cuisine.getCuisineName() %>
        </div>
    </div>
    <% } %>
</div>
<br><br>
<div class="menu-items-container">
    <%
        List<RestaurantMenu> menuItems = (List<RestaurantMenu>) request.getAttribute("restaurantMenu");
        if (menuItems != null && !menuItems.isEmpty() ) {
            for (RestaurantMenu menuItem : menuItems) {
    %>
    <div class="menu-item">
        <img src="<%= menuItem.getItemsImage().getImageUrl() %>" alt="<%= menuItem.getItemName() %>">
        <div class="menu-item-details">
            <h2><%= menuItem.getItemName() %>
            </h2>
            <label>Cuisine:</label>
            <span><%= menuItem.getCuisineCategory().getCuisineName() %></span>
            <br>
            <label>Veg/Non-veg:</label>
            <span><%= menuItem.isVeg() ? "Vegetarian" : "Non-vegetarian" %></span>
            <br>
            <label>Price:</label>
            <span><%= menuItem.getPrice() %></span>
            <br>
            <label>Ingredients:</label>
            <span><%= menuItem.getIngredients() %></span>
            <br>
            <label>Availability:</label>
            <span><%= menuItem.isAvailability() ? "Available" : "Not available" %></span>
            <br>
            <label>Restaurant Name:</label>
            <span><%= menuItem.getRestaurant().getRestaurantName() %></span>
            <div>
                <a href="/viewRestaurantByCategory?cuisineId=<%= menuItem.getCategoryId()%>" class="add-to-cart-link">
                    <button class="add-to-cart-button">View</button>
                </a>
            </div>
        </div>
    </div>
    <%
            }
        }
    %>
</div>
</body>
</html>

