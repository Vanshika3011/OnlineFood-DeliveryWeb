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
            background-color: #F1CF7F;
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
    <script>
        function handleSubmit() {
            var selectedOption = document.querySelector('input[name="addressId"]:checked');
            if (selectedOption) {
                window.location = "/placeOrder?id=" + selectedOption.value;
            } else {
                alert("Please select an option");
            }
        }
    </script>
</head>
<body>
<jsp:include page="customerDashboard.jsp"/>
<div class="add-restaurant" style="display: flex; justify-content: center; align-items: center;">
    <a href="/addAddressPage">Add Address</a>
</div>

<p style="color: red">${requestScope.orderError}</p>
<p style="color: red">${requestScope.addressError}</p>
<table>
    <tr>
        <th>Type</th>
        <th>Address</th>
        <th>LandMark</th>
        <th>City</th>
        <th>State</th>
        <th>Select</th>
    </tr>
    <%
        List<UserAddress> userAddressList = (List<UserAddress>) request.getAttribute("userAddressList");
        if (userAddressList != null && !userAddressList.isEmpty()) {
            for (UserAddress userAddress : userAddressList) {
    %>
    <tr>
        <td><%= userAddress.getAddressType()%>
        </td>
        <td><%= userAddress.getStreetLine1() + ", " + userAddress.getStreetLine2()%>
        </td>
        <td><%= userAddress.getLandmark()%>
        </td>
        <td><%= userAddress.getCity().getCityName()%>
        </td>
        <td><%= userAddress.getState().getStateName()%>
        </td>
        <td>
            <input type="radio" id="addressId" name="addressId" value="<%= userAddress.getUserAddressId()%>">
        </td>
    </tr>
    <% }
    %>
</table>
<div class="add-restaurant" style="display: flex; justify-content: center; align-items: center; ">
    <button onclick="handleSubmit()">Place Order</button>
</div>
<% } %>
</body>
</html>
