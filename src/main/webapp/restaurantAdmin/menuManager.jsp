<%@ page import="com.narola.finalproject.model.RestaurantMenu" %>
<%@ page import="java.util.List" %>
<%@ page import="com.narola.finalproject.model.CuisineCategory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cuisineFilter = request.getParameter("cuisine");
    String availabilityFilter = request.getParameter("availability");
    String typeFilter = request.getParameter("type");
    String sort = request.getParameter("sort");
%>
<html>
<head>
    <title>Menu</title>
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


        .addContainer {
            display: flex;
            justify-content: flex-end;
        }

        .btn {
            padding: 10px 20px;
            background-color: blue;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
        }

        .btn:hover {
            background-color: #003366;
        }

        .sort-form {
            margin-bottom: 20px;
        }

        .sort-select {
            padding: 8px;
            width: 200px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .sort-button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        .sort-button:hover {
            background-color: #45a049;
        }

        .image {
            width: 62px;
            height: 62px;
        }

        .selected {
            font-weight: bold;
        }
    </style>
    <script>
        function setSelectedOption(selectElement, value) {
            for (var i = 0; i < selectElement.options.length; i++) {
                if (selectElement.options[i].value === value) {
                    selectElement.options[i].selected = true;
                    break;
                }
            }
        }

        function applyFilters() {
            var cuisine = document.getElementById("cuisineFilter").value;
            var availability = document.getElementById("availabilityFilter").value;
            var type = document.getElementById("typeFilter").value;
            var sort = document.getElementById("sort").value;

            var baseUrl = "/menuManager";
            var queryParams = [];

            if (cuisine) {
                queryParams.push("cuisine=" + encodeURIComponent(cuisine));
            }
            if (availability) {
                queryParams.push("availability=" + encodeURIComponent(availability));
            }
            if (type) {
                queryParams.push("type=" + encodeURIComponent(type));
            }
            if (type) {
                queryParams.push("sort=" + encodeURIComponent(sort));
            }

            var url = baseUrl + (queryParams.length > 0 ? "?" + queryParams.join("&") : "");
            window.location.href = url;
        }

        window.addEventListener("DOMContentLoaded", function () {
            var cuisineFilter = document.getElementById("cuisineFilter");
            var availabilityFilter = document.getElementById("availabilityFilter");
            var typeFilter = document.getElementById("typeFilter");
            var sort = document.getElementById("sort");

            // Set the selected options based on the parameters
            setSelectedOption(cuisineFilter, "<%= cuisineFilter %>");
            setSelectedOption(availabilityFilter, "<%= availabilityFilter %>");
            setSelectedOption(typeFilter, "<%= typeFilter %>");
            setSelectedOption(sort, "<%= sort%>")

            cuisineFilter.addEventListener("change", function () {
                cuisineFilterState = this.value;
                applyFilters();
            });

            availabilityFilter.addEventListener("change", function () {
                availabilityFilterState = this.value;
                applyFilters();
            });

            typeFilter.addEventListener("change", function () {
                typeFilterState = this.value;
                applyFilters();
            });

            sort.addEventListener("change", function () {
                sortState = this.value;
                applyFilters();
            });
        });
    </script>
</head>
<body>
<jsp:include page="restaurantAdminDashboard.jsp"/>
<div class="addContainer">
    <a href="/addMenuItem" class="btn">Add</a>
</div>

<div class="filter-container">
    <label for="cuisineFilter">Cuisine:</label>
    <select id="cuisineFilter" onchange="applyFilters()">
        <option value="">All</option>
        <% List<CuisineCategory> cuisines = (List<CuisineCategory>) request.getAttribute("cuisineCategoryList");
            for (CuisineCategory cuisine : cuisines) { %>
        <option value="<%= cuisine.getCuisineId()%>"><%= cuisine.getCuisineName()%>
        </option>
        <% } %>
    </select>

    <label for="availabilityFilter">Availability:</label>
    <select id="availabilityFilter" onchange="applyFilters()">
        <option value="">All</option>
        <option value="true">Yes</option>
        <option value="false">No</option>
    </select>

    <label for="typeFilter">Type:</label>
    <select id="typeFilter" onchange="applyFilters()">
        <option value="">All</option>
        <option value="true">Veg</option>
        <option value="false">Non-Veg</option>
    </select>

    <label for="sort">Sort By:</label>
    <select id="sort" onchange="applyFilters()">
        <option value="">Select</option>
        <option value="nameAsc">Name(A-Z)</option>
        <option value="nameDesc">Name(Z-A)</option>
        <option value="priceLow">Price(Low-High)</option>
        <option value="priceHigh">Price(High-Low)</option>
        <option value="latest">Latest Added</option>
    </select>
</div>
<table>
    <tr>
        <th>Image</th>
        <th>Name</th>
        <th>Price</th>
        <th>Veg/Non</th>
        <th>Availability</th>
        <th>Ingredients</th>
        <th>Actions</th>
    </tr>
    <%
        List<RestaurantMenu> restaurantMenuList = (List<RestaurantMenu>) request.getAttribute("restaurantMenuList");
        if (!restaurantMenuList.isEmpty() || restaurantMenuList == null) {
            for (RestaurantMenu restaurantMenu : restaurantMenuList) {
                int itemId = restaurantMenu.getItemId();
    %>
    <tr>
        <td><img class="image" src="<%=  restaurantMenu.getItemsImage().getImageUrl()%>"
                 alt="<%= restaurantMenu.getItemName()%>">
        </td>
        <td><%= restaurantMenu.getItemName()%>
        </td>
        <td><%= restaurantMenu.getPrice()%>
        </td>
        <td><%= ((restaurantMenu.isVeg()) ? "Veg" : "Non-Veg")%>
        </td>
        <td><%= ((restaurantMenu.isAvailability()) ? "Yes" : "No")%>
        </td>
        <td><%= restaurantMenu.getIngredients()%>
        </td>
        <td>
            <a class="edit-link" href="/editMenuItem?id=<%= itemId %>">Edit</a>
            <a class="delete-link" href="/deleteMenuItem?id=<%= itemId %>">Delete</a>
        </td>
    </tr>
    <% }
    }%>
</table>
</body>
</html>
