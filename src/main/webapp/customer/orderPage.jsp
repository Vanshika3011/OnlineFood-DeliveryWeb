<%@ page import="com.narola.finalproject.model.OrderDetail" %>
<%@ page import="com.narola.finalproject.model.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="com.narola.finalproject.utility.Utility" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Order Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        .order-block-main {
            border: 1px solid #ccc;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .order-block {
            display: flex;
            border: 1px solid #ccc;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .order-details,
        .customer-details {
            flex: 1;
            margin-right: 20px;
        }

        .order-details h2,
        .customer-details h2 {
            margin-top: 0;
            margin-bottom: 10px;
        }

        .menu-items {
            margin-bottom: 20px;
        }

        .menu-items table {
            width: 100%;
            border-collapse: collapse;
        }

        .menu-items th,
        .menu-items td {
            padding: 10px;
            border: 1px solid #ccc;
        }

        .customer-details p {
            margin: 0;
        }

        .customer-details .label {
            font-weight: bold;
        }

        .customer-details .value {
            margin-left: 10px;
        }

        img{
            height: 50px;
            width: 50px;
        }
    </style>
</head>
<body>
<jsp:include page="customerDashboard.jsp"/>
<%
    List<Order> userOrderList = (List<Order>) request.getAttribute("userOrderList");
    if (userOrderList == null || userOrderList.isEmpty()) {
%>
<h1>No orders found</h1>
<%
} else {
    for(Order orderList : userOrderList){
        if (orderList.getRazorpayPaymentDetails().getCurrency() != null) {
%>
<div class="order-block-main">
    <div class="order-block">
        <div class="order-details">
            <h2>Order Details</h2>
            <p>Order ID: <%= orderList.getOrderId()%> </p>
            <p>Order Date: <%= Utility.formatDate(orderList.getOrderDate())%></p>
            <p>Restaurant name: <%= orderList.getRestaurantName()%></p>
            <p>Order Status: <%= orderList.getOrderStatusName()%></p>
        </div>

        <div class="customer-details">
            <h2>Customer Details</h2>
            <p><span class="label">Address Type:</span> <span class="value"><%= orderList.getUserAddress().getAddressType()%></span></p>
            <p><span class="label">Address:</span> <span class="value"><%= orderList.getUserAddress().getStreetLine1() + ", " +orderList.getUserAddress().getStreetLine2() + ", " +orderList.getUserAddress().getLandmark() + ", " +orderList.getUserAddress().getCity().getCityName() + ", " +orderList.getUserAddress().getState().getStateName() + ", " +orderList.getUserAddress().getPincode()%></span></p>
            <p><span class="label">Amount:</span> <span class="value"><%= orderList.getRazorpayPaymentDetails().getCurrency() + " " +orderList.getTotalAmount()%></span></p>
            <p><span class="label">Payment Method:</span> <span class="value"><%= orderList.getRazorpayPaymentDetails().getPaymentMethod()%></span></p>
            <p><span class="label">Payment Details:</span> <span class="value"><%= orderList.getRazorpayPaymentDetails().getPaymentDetails()%></span></p>
            <p><span class="label">Payment Status:</span> <span class="value"><%= orderList.getRazorpayPaymentDetails().getStatus()%></span></p>
        </div>
    </div>

    <div class="menu-items">
        <h2>Menu Items</h2>
        <table>
            <thead>
            <tr>
                <th></th>
                <th>Item Name</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
            </thead>
            <tbody>
            <% for(OrderDetail orderDetail : orderList.getOrderDetailList()){ %>
            <tr>
                <td><img src="<%= orderDetail.getImageUrl()%>" alt="<%= orderDetail.getItemName()%>"></td>
                <td><%= orderDetail.getItemName()%></td>
                <td><%= orderDetail.getQuantity()%></td>
                <td><%= orderList.getRazorpayPaymentDetails().getCurrency() + " " + orderDetail.getPrice()%></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
<%
            }
        }
    }
%>
</body>
</html>
