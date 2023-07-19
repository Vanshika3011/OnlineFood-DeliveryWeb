<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Cuisine</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ffffff;
        }

        .container {
            width: 400px;
            margin: 0 auto;
            margin-top: 100px;
            background-color: #f2f2f2;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"] {
            width: 99%;
            padding: 10px;
            border-radius: 3px;
            border: 1px solid #ccc;
        }

        .submit-btn {
            background-color: #4CAF50;
            color: #ffffff;
            border: none;
            padding: 10px;
            width: 100%;
            border-radius: 3px;
            cursor: pointer;
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
<jsp:include page="adminDashboard.jsp"/>
<div class="container">
    <h1>Cuisine Name</h1>
    <form action="/addCuisine" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <p style="color: red">${cuisineError}</p>
            <p style="color: red">${requestScope.cuisineNameError1} ${requestScope.cuisineNameError}</p>

            <label for="cuisineName">Enter the Cuisine Name :</label>
            <input type="text" id="cuisineName" name="cuisineName">

            <p style="color: red">${requestScope.cuisineImageError}</p>
            <label for="image">Image:</label>
            <input type="file" id="image" name="image" onchange="previewImage(event)">

            <div class="image-preview">
                <img id="preview-img" src="#" alt="Image Preview">
            </div>

        </div>
        <input type="submit" class="submit-btn" value="Add">
    </form>
</div>
</body>
</html>
