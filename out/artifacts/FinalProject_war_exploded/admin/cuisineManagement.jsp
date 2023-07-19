<%@ page import="java.util.List" %>
<%@ page import="com.narola.finalproject.model.CuisineCategory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cuisine Management</title>
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

        .edit-link, .delete-link {
            padding: 6px 10px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            margin-right: 5px;
        }

        .delete-link {
            background-color: #f44336;
        }

        .addContainer{
            display: flex;
            justify-content: flex-end;
        }
        .btn {
            padding: 10px 20px;
            background-color: blue;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
        }

        .btn:hover {
            background-color: #003366;
        }

        .image {
            width: 62px;
            height: 62px;
        }
    </style>
</head>
<body>
<jsp:include page="adminDashboard.jsp" />

<div class = "addContainer">
    <a href="/addCuisine" class="btn">Add</a>
</div>
<table>
    <tr>
        <th></th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    <p style="color: red">${cuisineError}</p>
    <%
        List<CuisineCategory> cuisineCategoryList = (List<CuisineCategory>) request.getAttribute("cuisineCategoryList");
        if(!cuisineCategoryList.isEmpty() || cuisineCategoryList == null){
        for (CuisineCategory cuisineCategory : cuisineCategoryList) {
            String cuisineName = cuisineCategory.getCuisineName();
            int cuisineId = cuisineCategory.getCuisineId();
    %>
    <tr>
        <td><img class="image" src="<%= cuisineCategory.getCuisineImageURL() %>" alt="<%= cuisineName  %>"></td>
        <td><%= cuisineName%></td>
        <td>
            <a class="edit-link" href="/editCuisineItem?id=<%= cuisineId%>">Edit</a>
            <a class="delete-link" href="/deleteCuisineItem?id=<%= cuisineId%>">Delete</a>
        </td>
    </tr>
    <%
            }}
    %>
</table>

</body>
</html>
