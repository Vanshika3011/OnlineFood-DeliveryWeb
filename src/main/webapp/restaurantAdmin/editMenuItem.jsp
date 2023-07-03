<%@ page import="com.narola.finalproject.model.CuisineCategory" %>
<%@ page import="java.util.List" %>
<%@ page import="com.narola.finalproject.model.RestaurantMenu" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Menu Item</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .container {
            margin: 20px auto;
            width: 400px;
            padding: 20px;
            background-color: #fff3f3;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: inline-block;
            width: 120px;
            font-weight: bold;
        }

        select, input[type="text"] {
            padding: 8px;
            width: 200px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<jsp:include page="restaurantAdminDashboard.jsp"/>
<%
    RestaurantMenu restaurantMenu = (RestaurantMenu) request.getAttribute("restaurantMenu");
%>
<div class="container">
    <h2>Edit Menu Item</h2>
    <form action="/editMenuItem?id=<%= restaurantMenu.getItemId()%>" method="post">
        <div class="form-group">
            <p style="color: red">${requestScope.itemNameError}</p>
            <label for="itemName">Item Name:</label>
            <input type="text" id="itemName" name="itemName" value="<%= restaurantMenu.getItemName()%>" >
        </div>
        <p style="color: red">${requestScope.categoryIdError}</p>
        <div class="form-group">
            <label for="category">Category:</label>
            <select id="category" name="category">
                <%
                    List<CuisineCategory> cuisineCategoryList = (List<CuisineCategory>) request.getAttribute("cuisineCategoryList");
                    if(!cuisineCategoryList.isEmpty() || cuisineCategoryList != null){
                        for (CuisineCategory cuisineCategory : cuisineCategoryList) {
                            String cuisineName = cuisineCategory.getCuisineName();
                            int cuisineId = cuisineCategory.getCuisineId();
                %>
                <option value="<%= cuisineId%>"<% if (cuisineId == restaurantMenu.getCategoryId()) { %>selected<% } %>><%= cuisineName%></option>
                <%
                        }
                %>
            </select>
            <%
                }
            %>
        </div>
        <p style="color: red">${requestScope.foodTypeError}</p>
        <div class="form-group">
            <label>Type:</label>
            <label><input type="radio" name="foodType" value="true" <% if (restaurantMenu.isVeg()) { %>checked<% } %> > Veg</label>
            <label><input type="radio" name="foodType" value="false" <% if (!restaurantMenu.isVeg()) { %>checked<% } %>> NonVeg</label>
        </div>
        <p style="color: red">${requestScope.priceError}</p>
        <div class="form-group">
            <label for="price">Price:</label>
            <input type="text" id="price" name="price" value="<%= restaurantMenu.getPrice()%>" >
        </div>
        <p style="color: red">${requestScope.ingredientsError}</p>
        <div class="form-group">
            <label for="ingredients">Ingredients:</label>
            <input type="text" id="ingredients" name="ingredients" value="<%= restaurantMenu.getIngredients()%>" >
        </div>
        <div class="form-group">
            <label>Availability:</label>
            <label><input type="radio" name="availability" value="true" <% if (restaurantMenu.isAvailability()) { %>checked<% } %> > Yes</label>
            <label><input type="radio" name="availability" value="false" <% if (!restaurantMenu.isAvailability()) { %>checked<% } %>> No</label>
        </div>
        <input type="hidden" name="itemId" value="<%= restaurantMenu.getItemId()%>">
        <div class="form-group">
            <input type="submit" value="Edit">
        </div>
    </form>
</div>
</body>
</html>
