<%@ page import="com.narola.finalproject.enums.UserRolesEnum" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ffffff;
            margin: 0;
            padding: 0;
        }

        .header {
            background-color: #555555;
            color: #ffffff;
            padding: 20px;
            text-align: center;
        }

        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #f3f3f3;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>

<jsp:include page="adminDashboard.jsp"/>
<div class="header">
    <h1>Welcome, <%= request.getAttribute("firstName") + " " + request.getAttribute("lastName")%>!</h1>
</div>

<div class="container">
    <h2>Admin </h2>
</div>
</body>
</html>
