<%@ page import="com.narola.finalproject.model.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="com.narola.finalproject.utility.Utility" %>
<%@ page import="com.narola.finalproject.constant.AppConstant" %>
<!DOCTYPE html>
<html>
<head>
    <title>Orders</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ccc;
        }

        th {
            background-color: #fff3f3;
        }

        .actions {
            text-align: center;
        }

        .view-button {
            padding: 5px 10px;
            background-color: dodgerblue;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            text-decoration-line: none;
        }

        .accept-button {
            padding: 5px 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            text-decoration-line: none;
        }

        .reject-button {
            padding: 5px 10px;
            background-color: red;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            text-decoration-line: none;
        }
    </style>
</head>
<body>
<jsp:include page="restaurantAdminDashboard.jsp"/>
<h1>All Orders</h1>

<table>
    <thead>
    <tr>
        <th>Order ID</th>
        <th>Customer Name</th>
        <th>Order Date</th>
        <th>Amount</th>
        <th>Payment Method</th>
        <th>Payment Status</th>
        <th>Order Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <% List<Order> orderList = (List<Order>) request.getAttribute("orderList");
        if (orderList == null || orderList.isEmpty()) {%>
    <h1>No Orders Found</h1>
    <% } else {
        for (Order order : orderList) {
            if (order.getRazorpayPaymentDetails().getCurrency() != null) {%>
    <tbody>
    <tr>
        <td><%= order.getOrderId()%>
        </td>
        <td><%= order.getCustomerName()%>
        </td>
        <td><%= Utility.formatDate(order.getOrderDate())%>
        </td>
        <td><%= order.getRazorpayPaymentDetails().getCurrency() + " " + order.getTotalAmount()%>
        </td>
        <td><%= order.getRazorpayPaymentDetails().getPaymentMethod()%>
        </td>
        <td><%= order.getRazorpayPaymentDetails().getStatus()%>
        </td>
        <td><%= order.getOrderStatusName()%>
        </td>
        <td class="actions">
            <a href="/viewOrderDetails?id=<%= order.getOrderId()%>" class="view-button">View</a>
            <% if (order.getOrderStatusId() == AppConstant.ORDER_STATUS_NEW) {%>
            <a href="/updateOrderStatus?id=<%= order.getOrderId() + "&statusId=" + AppConstant.ORDER_STATUS_ACCEPT%>"
               class="accept-button">Accept</a>
            <a href="/updateOrderStatus?id=<%= order.getOrderId() + "&statusId=" + AppConstant.ORDER_STATUS_REJECTED%>"
               class="reject-button">Reject</a>
            <%} else if (order.getOrderStatusId() == AppConstant.ORDER_STATUS_ACCEPT) {%>
            <a href="/updateOrderStatus?id=<%= order.getOrderId() + "&statusId=" + AppConstant.ORDER_STATUS_INPREPARATION %>"
               class="accept-button">In-Preparation</a>
            <%} else if (order.getOrderStatusId() == AppConstant.ORDER_STATUS_INPREPARATION) {%>
            <a href="/updateOrderStatus?id=<%= order.getOrderId() + "&statusId=" + AppConstant.ORDER_STATUS_ORDER_READY %>"
               class="accept-button">Prepared</a>
            <%} else if (order.getOrderStatusId() == AppConstant.ORDER_STATUS_ORDER_READY) {%>
            <a href="/updateOrderStatus?id=<%= order.getOrderId() + "&statusId=" + AppConstant.ORDER_STATUS_ON_THE_WAY %>"
               class="accept-button">On The Way</a>
            <%} else if (order.getOrderStatusId() == AppConstant.ORDER_STATUS_ON_THE_WAY) {%>
            <a href="/updateOrderStatus?id=<%= order.getOrderId() + "&statusId=" + AppConstant.ORDER_STATUS_DELIVERED %>"
               class="accept-button">Delivered</a>
            <%}%>
        </td>
    </tr>
    </tbody>
    <%
                }
            }
        }
    %>
</table>
</body>
</html>
