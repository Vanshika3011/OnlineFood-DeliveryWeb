<%@ page import="com.narola.finalproject.model.UserRole" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignUp</title>
    <style>

        body {
            background-color: #f0f0f0;
            font-family: Arial, sans-serif;
        }

        .container {
            max-width: 400px;
            margin: 100px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            margin-bottom: 30px;
        }

        input[type="text"],
        input[type="password"],
        input[type="tel"],
        input[type="email"],
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            border-radius: 5px;
            border: none;
            background-color: #4CAF50;
            color: #fff;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Sign Up</h2>
    <form method="post" action="signUp">
        <label for="role">Role:</label>
        <select id="role" name="role">
            <%
                List<UserRole> userRoleList = (List<UserRole>) request.getAttribute("userRoleList");
                for (UserRole userRole : userRoleList) {
                    String userRoleName = userRole.getUserRoleName();
                    int userRoleId = userRole.getUserRoleId();
            %>
            <option value="<%= userRoleId%>"><%= userRoleName%></option>
            <%
                }
            %>
        </select>

        <p style="color: red">${requestScope.userNameError} ${requestScope.userNameError1}</p>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" >

        <p style="color: red">${requestScope.passwordError}</p>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password">

        <p style="color: red">${requestScope.firstNameError}</p>
        <label for="firstname">First Name:</label>
        <input type="text" id="firstname" name="firstname" >

        <label for="lastname">Last Name:</label>
        <input type="text" id="lastname" name="lastname">

        <p style="color: red">${requestScope.contactNoError} ${requestScope.contactNoError1}</p>
        <label for="contact">Contact Number:</label>
        <input type="tel" id="contact" name="contact">

        <p style="color: red">${requestScope.emailError} ${requestScope.emailError1}</p>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email">

        <input type="submit" value="Sign Up">
    </form>
</div>

</body>
</html>
