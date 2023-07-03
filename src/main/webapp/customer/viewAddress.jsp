<%@ page import="com.narola.finalproject.model.UserAddress" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Address</title>
    <style>

            .add-restaurant {
                display: flex;
                align-items: center;
                height: 100px;
            }

            .add-restaurant a, .add-restaurant button {
                margin-left: 10px;
                padding: 5px 10px;
                border: none;
                background-color: #F9dfa2;
                color: #333;
                cursor: pointer;
                text-decoration: none;
            }

            .add-restaurant a:hover, .add-restaurant button:hover {
                background-color: #333;
                color: #F9dfa2;
            }

            table {
                width: 100%;
                border-collapse: collapse;
            }

            th, td {
                padding: 8px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #f2f2f2;
            }

    </style>
</head>
<body>
<jsp:include page="customerDashboard.jsp"/>
<div class="add-restaurant" style="display: flex; justify-content: center; align-items: center;">
    <a href="/addAddressPage">Add Address</a>
</div>
<table>
    <tr>
        <th>Type</th>
        <th>Address</th>
        <th>LandMark</th>
        <th>City</th>
        <th>State</th>
        <th>Actions</th>
    </tr>
    <%
        List<UserAddress> userAddressList = (List<UserAddress>) request.getAttribute("userAddressList");
        if (!userAddressList.isEmpty() || userAddressList == null) {
        for(UserAddress userAddress : userAddressList){
    %>

    <tr>
        <td><%= userAddress.getAddressType()%>
        </td>
        <td><%= userAddress.getStreetLine1() +", "+ userAddress.getStreetLine2()%>
        </td>
        <td><%= userAddress.getLandmark()%>
        </td>
        <td><%= userAddress.getCity().getCityName()%>
        </td>
        <td><%= userAddress.getState().getStateName()%>
        </td>
        <td>
            <a class="add-link"
               href="/addToCart?itemId=<%= userAddress.getUserAddressId()%>&restaurantId=<%= userAddress.getUserAddressId()%>">Select
            </a>
        </td>
    </tr>
    <% }
    }%>
</table>
<div class="add-restaurant" style="display: flex; justify-content: center; align-items: center;">
    <a href="/addAddressPage">Proceed for Payment</a>
</div>
</body>
</html>
