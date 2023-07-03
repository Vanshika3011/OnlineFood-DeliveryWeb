
<%@ page import="java.util.List" %>
<%@ page import="com.narola.finalproject.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String filter=request.getParameter("filterBy");
%>
<html>
<head>
    <title>User Management</title>
    <h3 style="color: red">${error}</h3>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th:last-child, td:last-child {
            text-align: center;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        .delete-link, .view-link {
            padding: 6px 10px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            margin-right: 5px;
        }

        .delete-link {
            background-color: #f44336;
        }

        .filter-container {
            display: flex;
            align-items: center;
            justify-content: flex-start;
            margin-bottom: 20px;
        }

        .dropdown {
            position: relative;
            display: inline-block;
            margin-left: 10px;
        }

        .dropdown select {
            padding: 8px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            background-color: #fff;
            color: #333;
            cursor: pointer;
        }

        .dropdown span {
            font-size: 16px;
            font-weight: bold;
            margin-right: 5px;
            color: #333;
        }

    </style>

    <script>
        function callServlet() {
            var dropdown = document.getElementById("filterDropdown");
            var selectedValue = dropdown.value;
            if(selectedValue==="default"){
                window.location.href = "/userManagement";
            }else{
            window.location.href = "/userManagement?filterBy=" + selectedValue;
        }}
    </script>
</head>
<body>
<jsp:include page="adminDashboard.jsp"/>
<div class="filter-container">
    <div class="dropdown">
        <span>View by Role:</span>
        <select id="filterDropdown" onchange="callServlet()">
            <option value="default" <%= filter==null?"selected":""%>>Default</option>
            <option value="admin" <%= (filter!=null && filter.equals("admin"))?"selected":""%>>Admin</option>
            <option value="restaurantAdmin" <%= (filter!=null && filter.equals("restaurantAdmin"))?"selected":""%>>Restaurant Admin</option>
            <option value="customer"<%= (filter!=null && filter.equals("customer"))?"selected":""%>>Customer</option>
            <option value="deliveryAgent" <%= (filter!=null && filter.equals("deliveryAgent"))?"selected":""%>>Delivery Agent</option>
        </select>
    </div>
</div>
<table>
    <tr>
        <th>Username</th>
        <th>Name</th>
        <th>Contact</th>
        <th>Email</th>
        <th>Actions</th>
    </tr>
    <%
        List<User> userList = (List<User>) request.getAttribute("userList");
        for (User user : userList) {
    %>
    <tr>
        <td><%= user.getUsername()%>
        </td>
        <td><%= user.getFirstName() + " " + user.getLastName()%>
        </td>
        <td><%= user.getContact()%>
        </td>
        <td><%= user.getEmail()%>
        </td>
        <td>
            <a class="delete-link" href="/deleteUser?id=<%= user.getUserId()%>">Delete</a>
        </td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>
