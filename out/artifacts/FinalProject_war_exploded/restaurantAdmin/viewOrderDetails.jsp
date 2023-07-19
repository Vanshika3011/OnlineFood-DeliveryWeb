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
<jsp:include page="restaurantAdminDashboard.jsp"/>

<% Order order = (Order) request.getAttribute("order");%>

<div class="order-block-main">
    <div class="order-block">
        <div class="order-details">
            <h2>Order Details</h2>
            <p>Order ID: <%= order.getOrderId()%> </p>
            <p>Order Date: <%= Utility.formatDate(order.getOrderDate())%></p>
            <p>Order Status: <%= order.getOrderStatusName()%></p>
            <p>Customer name: <%= order.getCustomerName()%></p>
            <p>Restaurant name: <%= order.getRestaurantName()%></p>
        </div>

        <div class="customer-details">
            <h2>Customer Details</h2>
            <p><span class="label">Address Type:</span> <span class="value"><%= order.getUserAddress().getAddressType()%></span></p>
            <p><span class="label">Address:</span> <span class="value"><%= order.getUserAddress().getStreetLine1() + ", " +order.getUserAddress().getStreetLine2() + ", " +order.getUserAddress().getLandmark() + ", " +order.getUserAddress().getCity().getCityName() + ", " +order.getUserAddress().getState().getStateName() + ", " +order.getUserAddress().getPincode()%></span></p>
            <p><span class="label">Amount:</span> <span class="value"><%= order.getRazorpayPaymentDetails().getCurrency() + " " +order.getTotalAmount()%></span></p>
            <p><span class="label">Payment Method:</span> <span class="value"><%= order.getRazorpayPaymentDetails().getPaymentMethod()%></span></p>
            <p><span class="label">Payment Details:</span> <span class="value"><%= order.getRazorpayPaymentDetails().getPaymentDetails()%></span></p>
            <p><span class="label">Payment Status:</span> <span class="value"><%= order.getRazorpayPaymentDetails().getStatus()%></span></p>
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
            <% for(OrderDetail orderDetail : order.getOrderDetailList()){ %>
            <tr>
                <td><img src="<%= orderDetail.getImageUrl()%>" alt="<%= orderDetail.getItemName()%>"></td>
                <td><%= orderDetail.getItemName()%></td>
                <td><%= orderDetail.getQuantity()%></td>
                <td><%= order.getRazorpayPaymentDetails().getCurrency() + " " + orderDetail.getPrice()%></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
