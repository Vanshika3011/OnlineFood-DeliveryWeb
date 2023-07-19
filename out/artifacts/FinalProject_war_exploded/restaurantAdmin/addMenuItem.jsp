<%@ page import="com.narola.finalproject.model.CuisineCategory" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Menu Item</title>
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
        .image-preview {
            margin-left: 20px;
        }

        .image-preview img {
            max-width: 200px;
            max-height: 200px;
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
<jsp:include page="restaurantAdminDashboard.jsp"/>
<div class="container">
    <h2>Add Menu Item</h2>
    <form action="/addMenuItem" method="post" enctype="multipart/form-data">
        <p style="color: red">${requestScope.itemNameError}</p>
        <div class="form-group">
            <label for="itemName">Item Name:</label>
            <input type="text" id="itemName" name="itemName" >
        </div>
        <p style="color: red">${requestScope.categoryIdError}</p>
        <div class="form-group">
            <label for="category">Category:</label>
            <select id="category" name="category">
                <%
                List<CuisineCategory> cuisineCategoryList = (List<CuisineCategory>) request.getAttribute("cuisineCategoryList");
                if(!cuisineCategoryList.isEmpty() || cuisineCategoryList == null){
                for (CuisineCategory cuisineCategory : cuisineCategoryList) {
                String cuisineName = cuisineCategory.getCuisineName();
                int cuisineId = cuisineCategory.getCuisineId();
                %>
                <option value="<%= cuisineId%>"><%= cuisineName%></option>
                <%
                    }}
                %>
            </select>
        </div>
        <p style="color: red">${requestScope.foodTypeError}</p>
        <div class="form-group">
            <label>Type:</label>
            <label><input type="radio" name="foodType" value="true" > Veg</label>
            <label><input type="radio" name="foodType" value="false" > NonVeg</label>
        </div>
        <p style="color: red">${requestScope.priceError}</p>
        <div class="form-group">
            <label for="price">Price:</label>
            <input type="text" id="price" name="price" >
        </div>
        <p style="color: red">${requestScope.ingredientsError}</p>
        <div class="form-group">
            <label for="ingredients">Ingredients:</label>
            <input type="text" id="ingredients" name="ingredients" >
        </div>
        <p style="color: red">${requestScope.imageUrlError}</p>
        <div class="form-group">
            <label for="image">Image:</label>
            <input type="file" id="image" name="image" onchange="previewImage(event)">
        </div>
        <div class="image-preview">
            <img id="preview-img" src="#" >
        </div>
        <div class="form-group">
            <input type="submit" value="Add Item">
        </div>
    </form>
</div>
</body>
</html>
