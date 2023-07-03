<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Verification Code</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }
        .container {
            width: 400px;
            margin: 0 auto;
            margin-top: 100px;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
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
            width: 100%;
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
    </style>
</head>
<body>
<div class="container">
    <h1>Verification Code</h1>
    <%-- Display error message if exists --%>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
    <% } %>
    <form action="/verifyUser" method="post">
        <div class="form-group">
            <label for="verificationCode">Enter the 6-digit verification code:</label>
            <input type="text" id="verificationCode" name="verificationCode" maxlength="6" pattern="[0-9]{6}" required>
            <p style="color: red">${verificationCodeError}</p>
        </div>
        <input type="submit" class="submit-btn" value="Verify">
    </form>
</div>
</body>
</html>
