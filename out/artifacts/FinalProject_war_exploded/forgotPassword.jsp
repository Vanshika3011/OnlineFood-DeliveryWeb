<%--
  Created by IntelliJ IDEA.
  User: lvanshika
  Date: 12-07-2023
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>Forgot Password</title>

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

    input[type="text"] {
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
  <h1>Forgot Password</h1>
  <form action="/forgotPassword" method="post">
    <br><br>
    <p style="color: red">${requestScope.error}</p>
    Enter Email ID:
    <p>
    <input type="text" name="email" /><br><br>
    </p>
    <input type="submit" />
  </form>
</div>
</body>
</html>
