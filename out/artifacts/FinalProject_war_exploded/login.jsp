<%@ page import="com.narola.finalproject.model.ErrorMessage" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
        }

        .container {
            width: 400px;
            margin: 0 auto;
            margin-top: 100px;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        h2 {
            text-align: center;
        }

        label, input {
            font-size: large;
            display: block;
            margin-bottom: 30px;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Login Page</h2>
    <form method="post" action="/doLogin">
        <p style="color: dodgerblue">${requestScope.message}</p>
        <p style="color: red">${requestScope.error}</p>

        <p style="color: red">${requestScope.userNameError}</p>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username">

        <p style="color: red">${requestScope.passwordError}</p>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password">

        <input type="submit" value="Login">
    </form>
    <p> <a href="/signUp">Don't have an account? Sign-up now</a></p>
    <p> <a href="/forgotPassword">Forgot Password?</a></p>
</div>

</body>
</html>
