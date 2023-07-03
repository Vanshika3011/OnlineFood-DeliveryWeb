<%@ page import="com.narola.finalproject.model.Restaurant" %>
<%@ page import="com.narola.finalproject.model.User" %>
<%@ page import="com.narola.finalproject.enums.UserRolesEnum" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<% HttpSession httpSession = request.getSession();
    User user = (User) httpSession.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Restaurant</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ffffff;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: #FFFFFF;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        h1 {
            color: #333333;
            text-align: center;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            color: #333333;
        }

        input[type="text"], select {
            width: 90%;
            padding: 10px;
            border: 1px solid #dddddd;
            border-radius: 4px;
            color: #555555;
        }

        button[type="submit"] {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }

        button[type="submit"]:hover {
            background-color: #45a049;
        }

        .image-preview {
            margin-left: 20px;
        }

        .image-preview img {
            max-width: 200px;
            max-height: 200px;
        }
        .submit-btn {
            background-color: #4CAF50;
            color: #ffffff;
            border: none;
            padding: 10px;
            width: 20%;
            border-radius: 3px;
            cursor: pointer;
        }
    </style>
    <script>
        function previewImage(event) {
            var input = event.target;
            var preview = document.querySelector('.image-preview img');

            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    preview.src = e.target.result;
                };
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</head>
<body>
<% if(user.getRoleId() == UserRolesEnum.RESTAURANTADMIN.getRoleIdValue()){ %>
<jsp:include page="restaurantAdminDashboard.jsp"/>
<% }
else{ %>
<jsp:include page="/admin/adminDashboard.jsp"/>
<% } %>
<div class="container">
    <%
        Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
    %>
    <h1>Edit Restaurant Details</h1>
    <form action="/editRestaurant?restaurantId=<%= restaurant.getRestaurantId()%>" method="post" enctype="multipart/form-data">
        <p style="color: red">${requestScope.restaurantNameError}</p>
        <div class="form-group">
            <label for="restaurantName">Name:</label>
            <input type="text" id="restaurantName" name="restaurantName" value="<%= restaurant.getRestaurantName() %>">
        </div>
        <p style="color: red">${requestScope.contactNoError}${requestScope.contactNoError1}</p>
        <div class="form-group">
            <label for="contact">Contact:</label>
            <input type="text" id="contact" name="contact" value="${ restaurant.getRestaurantContact() }">
        </div>
        <p style="color: red">${requestScope.emailError}</p>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" value="<%= restaurant.getRestaurantEmail() %>">
        </div>
        <p style="color: red">${requestScope.opensAtError}${requestScope.opensAtError1}</p>
        <div class="form-group">
            <label for="openingTime">Opening Time:</label>
            <input type="text" id="openingTime" name="openingTime" value="<%= restaurant.getOpensAt() %>">
        </div>
        <p style="color: red">${requestScope.closesAtError}${requestScope.closesAtError1}</p>
        <div class="form-group">
            <label for="closingTime">Closing Time:</label>
            <input type="text" id="closingTime" name="closingTime" value="<%= restaurant.getClosesAt() %>">
        </div>

        <div class="form-group">
            <label for="gstNumber">GST Number:</label>
            <input type="text" id="gstNumber" name="gstNumber" value="<%= restaurant.getGstNo() %>" disabled>
        </div>

        <div class="form-group">
            <label>Dine In:</label>
            <label><input type="radio" name="isDineInAvailable" value="true" <% if (restaurant.isDiningAvailable()) { %>checked<% } %> disabled> Yes</label>
            <label><input type="radio" name="isDineInAvailable" value="false" <% if (!restaurant.isDiningAvailable()) { %>checked<% }  %> disabled> No</label>
        </div>

        <h2>Address Details</h2>
        <p style="color: red">${requestScope.restaurantAddError}</p>
        <div class="form-group">
            <label for="addressLine1">Address Line 1:</label>
            <input type="text" id="addressLine1" name="addressLine1" value="<%= restaurant.getRestaurantAddress().getRestaurantAddLine1() %>">
        </div>
        <div class="form-group">
            <label for="addressLine2">Address Line 2:</label>
            <input type="text" id="addressLine2" name="addressLine2" value="<%= restaurant.getRestaurantAddress().getRestaurantAddLine2() %>">
        </div>

        <div class="form-group">
            <label for="state">State</label>
            <input type="text" id="state" name="stateId" value="<%= restaurant.getRestaurantAddress().getState().getStateName()  %>" disabled>

        </div>

        <div class="form-group">
            <label for="city">City</label>
            <input type="text" id="city" name="cityId" value="<%= restaurant.getRestaurantAddress().getCity().getCityName()  %>" disabled>
        </div>
        <p style="color: red">${requestScope.pincodeError}${requestScope.pincodeError1}</p>
        <div class="form-group">
            <label for="pincode">Pincode:</label>
            <input type="text" id="pincode" name="pincode" value="<%= restaurant.getRestaurantAddress().getPincode() %>">
        </div>

        <label for="image">Image:</label>
        <input type="file" id="image" name="image" onchange="previewImage(event)" >

        <div class="image-preview">
            <img id="preview-img" src="<%= restaurant.getRestaurantImageUrl()%>" alt="Image Preview">
        </div>

        <input type="hidden" name="imagePath" value="<%= restaurant.getRestaurantImageUrl()%>">
        <div class="form-group">
        <input type="submit" class="submit-btn" value="Edit">
        </div>
    </form>
</div>
</body>
</html>
