<%@ page import="com.narola.finalproject.model.RestaurantMenu" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu Page</title>
    <style>
        h1 {
            color: #333;
            font-size: 24px;
            margin-bottom: 10px;
        }

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

        .add-link {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .add-link:hover {
            text-decoration: underline;
            background-color: #45a049;
        }
    </style>

</head>
<body>
<jsp:include page="customerDashboard.jsp"/>
<p style="color: red">${error}</p>

<table>
    <tr>
        <th>Image</th>
        <th>Name</th>
        <th>Price</th>
        <th>Cuisine</th>
        <th>Veg/Non</th>
        <th>Restaurant</th>
        <th>Availability</th>
        <th>Ingredients</th>
        <th>Actions</th>
    </tr>
    <%
        List<RestaurantMenu> restaurantMenuList = (List<RestaurantMenu>) request.getAttribute("restaurantMenuList");
        if (!restaurantMenuList.isEmpty() || restaurantMenuList == null) {
            for (RestaurantMenu restaurantMenu : restaurantMenuList) {
    %>
    <tr>
        <td><img style="width: 120px ; height: 120px" src="<%= restaurantMenu.getItemsImage().getImageUrl()%>"
                 alt="<%= restaurantMenu.getItemName() %>">
        </td>
        <td><%= restaurantMenu.getItemName()%>
        </td>
        <td><%= restaurantMenu.getPrice()%>
        </td>
        <td><%= restaurantMenu.getCuisineCategory().getCuisineName()%>
        </td>
        <td><%= ((restaurantMenu.isVeg()) ? "Veg" : "Non-Veg")%>
        </td>
        <td><%= restaurantMenu.getRestaurant().getRestaurantName()%>
        </td>
        <td><%= ((restaurantMenu.isAvailability()) ? "Yes" : "No")%>
        </td>
        <td><%= restaurantMenu.getIngredients()%>
        </td>
        <td>
            <a class="add-link"
               href="/addToCart?itemId=<%= restaurantMenu.getItemId()%>&restaurantId=<%= restaurantMenu.getRestaurantId()%>">Add
                To Cart</a>
        </td>
    </tr>
    <% }
    }%>
</table>
</body>
</html>
