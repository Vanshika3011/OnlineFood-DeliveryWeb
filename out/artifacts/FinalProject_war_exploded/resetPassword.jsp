<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirm Password</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            font-size: large;
            background-color: #f1f1f1;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            width: 400px;
            padding: 20px;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #333;
            text-align: center;
        }

        form {
            margin-top: 30px;
        }

        label, input {
            font-size: large;
            display: block;
            margin-bottom: 30px;
        }

        input[type="password"] {
            width: 100%;
            padding: 5px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Confirm Password</h1>

    <form action="/resetPassword" method="post">
        <br><br>
        <p style="color: red">${requestScope.error}</p>
        Enter Password:<input type="password" name="password"/><br><br>
        Confirm Password: <input type="password" name="confirmPassword"/><br><br>
        <input type="hidden" name="email" value="<%= request.getAttribute("email")%>">
        <input type="submit" value="Confirm"/>
    </form>
</div>
</body>
</html>
