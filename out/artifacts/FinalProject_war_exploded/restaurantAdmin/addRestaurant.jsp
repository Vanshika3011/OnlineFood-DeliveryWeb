<%@ page import="com.narola.finalproject.model.State" %>
<%@ page import="com.narola.finalproject.dao.LocationDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Restaurant</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff3f3;
        }

        .container {
            max-width: 500px;
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
            width: 100%;
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
    </style>
    <script>
        function updateCities(stateId) {
            var cityDropdown = document.getElementById("city");
            cityDropdown.innerHTML = "";
            var citiesJspUrl = "/cities.jsp?stateId=" + stateId;

            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    cityDropdown.innerHTML = xhr.responseText;
                }
            };
            xhr.open("GET", citiesJspUrl, true);
            xhr.send();
        }

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
<div class="container">
    <h1>Add Restaurant</h1>
    <form action="/addRestaurant" method="post" enctype="multipart/form-data">
        <p style="color: red">${requestScope.restaurantNameError}</p>
        <div class="form-group">
            <label for="restaurantName">Name:</label>
            <input type="text" id="restaurantName" name="restaurantName">
        </div>
        <p style="color: red">${requestScope.contactNoError}${requestScope.contactNoError1}</p>
        <div class="form-group">
            <label for="contact">Contact:</label>
            <input type="text" id="contact" name="contact">
        </div>
        <p style="color: red">${requestScope.emailError}</p>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="text" id="email" name="email">
        </div>
        <p style="color: red">${requestScope.opensAtError}</p>
        <div class="form-group">
            <label for="openingTime">Opening Time:</label>
            <input type="text" id="openingTime" name="openingTime">
        </div>
        <p style="color: red">${requestScope.closesAtError}</p>
        <div class="form-group">
            <label for="closingTime">Closing Time:</label>
            <input type="text" id="closingTime" name="closingTime">
        </div>
        <p style="color: red">${requestScope.gstNumError}</p>
        <div class="form-group">
            <label for="gstNumber">GST Number:</label>
            <input type="text" id="gstNumber" name="gstNumber">
        </div>

        <div class="form-group">
            <label>Dine In:</label>
            <label><input type="radio" name="isDineInAvailable" value="true" > Yes</label>
            <label><input type="radio" name="isDineInAvailable" value="false"> No</label>
        </div>

        <h2>Address Details</h2>
        <p style="color: red">${requestScope.restaurantAddError}</p>
        <div class="form-group">
            <label for="addressLine1">Address Line 1:</label>
            <input type="text" id="addressLine1" name="addressLine1">
        </div>
        <div class="form-group">
            <label for="addressLine2">Address Line 2:</label>
            <input type="text" id="addressLine2" name="addressLine2">
        </div>
        <p style="color: red">${requestScope.stateIdError}</p>
        <div class="form-group">
            <label for="state">State</label>
            <select id="state" name="stateId" onchange="updateCities(this.value)">
                <option value="0">Select state</option>
                <% LocationDao locationDao = new LocationDao();
                    for (State state : locationDao.getStates()) { %>
                <option value="<%= state.getStateId() %>"><%= state.getStateName() %>
                </option>
                <% } %>
            </select>
        </div>

        <p style="color: red">${requestScope.cityIdError}</p>
        <div class="form-group">
            <label for="city">City</label>
            <select id="city" name="cityId" >
                <jsp:include page="/cities.jsp"/>
            </select>
        </div>
        <p style="color: red">${requestScope.pincodeError}${requestScope.pincodeError1}</p>
        <div class="form-group">
            <label for="pincode">Pincode:</label>
            <input type="text" id="pincode" name="pincode">
        </div>
        <p style="color: red">${requestScope.imageError}</p>
        <div class="form-group">
            <label for="image">Image:</label>
            <input type="file" id="image" name="image" onchange="previewImage(event)">
        </div>
        <div class="image-preview">
            <img id="preview-img" src="#" >
        </div>
        <button type="submit">Add Restaurant</button>
    </form>
</div>
</body>
</html>
