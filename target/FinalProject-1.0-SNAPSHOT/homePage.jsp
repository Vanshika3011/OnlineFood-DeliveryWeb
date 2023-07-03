
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f1f1f1;
        }

        h1 {
            color: #333;
            text-align: center;
            margin-top: 50px;
        }

        h2 {
            color: #666;
            margin-top: 30px;
            text-align: center;
        }

        form {
            text-align: center;
            margin-top: 50px;
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
<h1>Welcome to the Foodies</h1>


<h2> ${user.getFirstName()}</h2>


<form action="/homePage" method="post">
    <input type="submit" value="Proceed">
</form>
</body>
</html>

